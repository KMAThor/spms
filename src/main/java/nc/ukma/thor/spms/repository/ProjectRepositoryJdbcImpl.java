package nc.ukma.thor.spms.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.Trait;
import nc.ukma.thor.spms.entity.TraitCategory;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.util.SortingOrder;

@Repository
public class ProjectRepositoryJdbcImpl  implements ProjectRepository{
	
	private static final String INSERT_PROJECT_SQL =
			"INSERT INTO project (name, description, start_date, end_date, is_completed, chief_mentor_id) "
			+"VALUES (?,?,?,?,?,?);";
	
	private static final String UPDATE_PROJECT_SQL = 
			"UPDATE project SET name=?, description=?, start_date=?, "
			+ "end_date=?, is_completed=?, chief_mentor_id=? "
			+ "WHERE id=?;";
	
	private static final String DELETE_PROJECT_SQL = "DELETE FROM project WHERE id=?;";
	
	private static final String GET_PROJECT_BY_ID_SQL = "SELECT * FROM project WHERE id=?;";
	private static final String GET_ALL_ACTIVE_PROJECTS_SQL = "SELECT * FROM project WHERE is_completed = FALSE;";
	private static final String GET_ALL_PROJECTS_SQL = "SELECT * FROM project;";
	
	private static final String ADD_TRAIT_TO_PROJECT_SQL = "INSERT INTO trait_project (trait_id, project_id) VALUES(?,?);";
	private static final String DELETE_TRAIT_FROM_PROJECT_SQL = "DELETE FROM trait_project WHERE trait_id=? AND project_id=?;";
	
	private static final String GET_PROJECTS_BY_PAGE_SQL = "SELECT * FROM project "
			+ "WHERE name ILIKE ? "
			+ "OR to_char(start_date, 'HH12:MI:SS') ILIKE ? "
			+ "OR to_char(end_date, 'HH12:MI:SS') ILIKE ? "
			+ "ORDER BY %s %s, id "//default ordering by id, it is important for pagination
			+ "LIMIT ? OFFSET ?;";
	
	private static final String COUNT_PROJECTS_SQL = "SELECT COUNT (*) FROM project;";
	private static final String COUNT_PROJECTS_FILTERED_SQL = "SELECT COUNT (*) FROM project "
			+ "WHERE name ILIKE ? "
			+ "OR to_char(start_date, 'HH12:MI:SS') ILIKE ? "
			+ "OR to_char(end_date, 'HH12:MI:SS') ILIKE ?;";
	
	private static final RowMapper<Project> PROJECT_MAPPER = new ProjectMapper();

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private TraitRepository traitRepository;

	@Override
	public void add(Project p) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(INSERT_PROJECT_SQL, new String[] {"id"});
            ps.setString(1, p.getName());
            ps.setString(2, p.getDescription());
            ps.setTimestamp(3, p.getStartDate());
            ps.setTimestamp(4, p.getEndDate());
            ps.setBoolean(5, false);
            if(p.getChiefMentor() != null) ps.setLong(6, p.getChiefMentor().getId());
            else ps.setNull(6, java.sql.Types.BIGINT);
            return ps;
		},keyHolder);
		p.setId(keyHolder.getKey().longValue());		
	}
	
	@Override
	public void update(Project p) {
		Object [] values = {
				p.getName(), 
				p.getDescription(), 
				p.getStartDate(),
				p.getEndDate(),
				p.isCompleted(),
				null,
				p.getId()
		};
		if(p.getChiefMentor() != null) values[5] = p.getChiefMentor().getId();
		jdbcTemplate.update(UPDATE_PROJECT_SQL, values);
	}

	@Override
	public void delete(Project p) {
		jdbcTemplate.update(DELETE_PROJECT_SQL, p.getId());
	}
	
	@Override
	public Project getById(long id) {
		try{
			return jdbcTemplate.queryForObject(GET_PROJECT_BY_ID_SQL,
						new Object[] { id },
						PROJECT_MAPPER);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	@Override
	public List<Project> getProjects(long offset, int length, int orderBy, SortingOrder order, String searchValue) {
		String query = String.format(GET_PROJECTS_BY_PAGE_SQL,
				OrdableColumn.values()[orderBy].getColumnName(), order);
		String searchParam = "%" + searchValue + "%";
		return jdbcTemplate.query(query,
				new Object[] { searchParam,searchParam, searchParam, length, offset }, PROJECT_MAPPER);
	}

	
	
	
	@Override
	public Long count() {
		return this.jdbcTemplate.queryForObject(COUNT_PROJECTS_SQL, Long.class);
	}
	
	@Override
	public Long count(String searchValue) {
		String searchParam = "%" + searchValue + "%";
		return this.jdbcTemplate.queryForObject(COUNT_PROJECTS_FILTERED_SQL,
				new Object[] { searchParam, searchParam, searchParam}, Long.class);
	}
	
	@Override
	public List<Project> getAllActiveProjects() {
		return jdbcTemplate.query(GET_ALL_ACTIVE_PROJECTS_SQL, PROJECT_MAPPER);
	}

	@Override
	public List<Project> getAllProjects() {
		return jdbcTemplate.query(GET_ALL_PROJECTS_SQL, PROJECT_MAPPER);
	}
	
	@Override
	public void addTraitToProject(Trait trait, Project project) {
		jdbcTemplate.update(ADD_TRAIT_TO_PROJECT_SQL, trait.getId(), project.getId());
	}	
	@Override
	public void deleteTraitFromProject(Trait trait, Project project) {
		jdbcTemplate.update(DELETE_TRAIT_FROM_PROJECT_SQL, trait.getId(), project.getId());
	}
	
	@Override
	public int[] addTraitCategoryToProject(TraitCategory traitCategory, Project project) {
		List<Trait> traits = traitRepository.getTraitsByTraitCategoryAndNotFromProject(traitCategory, project);
        return jdbcTemplate.batchUpdate(ADD_TRAIT_TO_PROJECT_SQL,
        		prepareTraitsToBanchUpdate(traits, traitCategory,project));
	}	
	@Override
	public int[] deleteTraitCategoryFromProject(TraitCategory traitCategory, Project project) {
		List<Trait> traits = traitRepository.getTraitsByTraitCategoryAndProject(traitCategory, project);
		return jdbcTemplate.batchUpdate(DELETE_TRAIT_FROM_PROJECT_SQL,
        		prepareTraitsToBanchUpdate(traits, traitCategory,project));
	}
	
	private List<Object[]> prepareTraitsToBanchUpdate(List<Trait> traits, TraitCategory traitCategory, Project project){
		List<Object[]> batch = new ArrayList<Object[]>();
        for (Trait trait : traits) {
            Object[] values = new Object[] {
            		trait.getId(),
                    project.getId()};
            batch.add(values);
        }
        return batch;
	}
		
		
	private static final class ProjectMapper implements RowMapper<Project> {
		@Override
		public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
			Project pr = new Project();
			pr.setId(rs.getLong("id"));
			pr.setName(rs.getString("name"));
			pr.setDescription(rs.getString("description"));
			pr.setStartDate(rs.getTimestamp("start_date"));
			pr.setEndDate(rs.getTimestamp("end_date"));
			pr.setIsCompleted(rs.getBoolean("is_completed"));
			Long chiefMentorId = rs.getLong("chief_mentor_id");
			if(!rs.wasNull()) pr.setChiefMentor(new User(chiefMentorId));
			return pr;
		}
	}
	
	public static enum OrdableColumn {
		ID("id"), NAME("name"), START_DATE("start_date"), END_DATE("end_date"), IS_CMPLETED("is_completed");
		private String columnName;

		private OrdableColumn(String columnName) {
			this.columnName = columnName;
		}
		public String getColumnName(){
			return columnName;
		}
	}

	
}
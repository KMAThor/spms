package nc.ukma.thor.spms.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Trait;
import nc.ukma.thor.spms.entity.User;

@Repository
public class ProjectRepositoryJdbcImpl implements ProjectRepository{
	
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
	
	private static final RowMapper<Project> PROJECT_MAPPER = new ProjectMapper();

	@Autowired
	private JdbcTemplate jdbcTemplate;

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
				p.isCompleated(),
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
		
		
	private static final class ProjectMapper implements RowMapper<Project> {
		@Override
		public Project mapRow(ResultSet rs, int rowNum) throws SQLException {
			Project pr = new Project();
			pr.setId(rs.getLong("id"));
			pr.setName(rs.getString("name"));
			pr.setDescription(rs.getString("description"));
			pr.setStartDate(rs.getTimestamp("start_date"));
			pr.setEndDate(rs.getTimestamp("end_date"));
			pr.setCompleated(rs.getBoolean("is_completed"));
			Long chiefMentorId = rs.getLong("chief_mentor_id");
			if(!rs.wasNull()) pr.setChiefMentor(new User(chiefMentorId));
			return pr;
		}
	}

}
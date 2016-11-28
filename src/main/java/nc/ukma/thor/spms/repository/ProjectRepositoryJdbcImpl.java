package nc.ukma.thor.spms.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import nc.ukma.thor.spms.entity.*;
import nc.ukma.thor.spms.entity.report.ProjectReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import nc.ukma.thor.spms.util.SortingOrder;

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
	
	/*For chief mentor*/
	private static final String GET_PROJECTS_BY_CHIEF_MENTOR_BY_PAGE_SQL = "SELECT * FROM project "
			+ "WHERE (name ILIKE ? "
			+ "OR to_char(start_date, 'HH12:MI:SS') ILIKE ? "
			+ "OR to_char(end_date, 'HH12:MI:SS') ILIKE ?) AND chief_mentor_id=? "
			+ "ORDER BY %s %s, id "//default ordering by id, it is important for pagination
			+ "LIMIT ? OFFSET ?;";
	private static final String COUNT_PROJECTS_BY_CHIEF_MENTOR_SQL = "SELECT COUNT (*) "
			+ "FROM project "
			+ "WHERE chief_mentor_id=?;";
	private static final String COUNT_PROJECTS_BY_CHIEF_MENTOR_FILTERED_SQL = "SELECT COUNT (*) FROM project "
			+ "WHERE (name ILIKE ? "
			+ "OR to_char(start_date, 'HH12:MI:SS') ILIKE ? "
			+ "OR to_char(end_date, 'HH12:MI:SS') ILIKE ?) AND chief_mentor_id=?;";
	
	/*For mentor*/
	private static final String GET_PROJECTS_BY_MENTOR_BY_PAGE_SQL = "SELECT * FROM project "
			+ "LEFT JOIN team ON project.id = team.project_id "
			+ "INNER JOIN user_team ON team.id = user_team.team_id "
			+ "INNER JOIN \"user\" ON user_team.user_id = \"user\".id "
			+ "INNER JOIN user_role ON \"user\".id = user_role.user_id "
			+ "INNER JOIN role ON user_role.role_id = role.id "
			+ "WHERE (project.name ILIKE ? "
			+ "OR to_char(start_date, 'HH12:MI:SS') ILIKE ? "
			+ "OR to_char(end_date, 'HH12:MI:SS') ILIKE ?) AND (chief_mentor_id=? OR (user_team.user_id=? AND role.role=?)) "
			+ "ORDER BY project.%s %s, project.id "//default ordering by id, it is important for pagination
			+ "LIMIT ? OFFSET ?;";
	
	private static final String COUNT_PROJECTS_BY_MENTOR_SQL = "SELECT COUNT (*) FROM project "
			+ "LEFT JOIN team ON project.id = team.project_id "
			+ "INNER JOIN user_team ON team.id = user_team.team_id "
			+ "INNER JOIN \"user\" ON user_team.user_id = \"user\".id "
			+ "INNER JOIN user_role ON \"user\".id = user_role.user_id "
			+ "INNER JOIN role ON user_role.role_id = role.id "
			+ "WHERE chief_mentor_id=? OR (user_team.user_id=? AND role.role=?);";
	
	private static final String COUNT_PROJECTS_BY_MENTOR_FILTERED_SQL = "SELECT COUNT (*) FROM project "
			+ "LEFT JOIN team ON project.id = team.project_id "
			+ "INNER JOIN user_team ON team.id = user_team.team_id "
			+ "INNER JOIN \"user\" ON user_team.user_id = \"user\".id "
			+ "INNER JOIN user_role ON \"user\".id = user_role.user_id "
			+ "INNER JOIN role ON user_role.role_id = role.id "
			+ "WHERE (project.name ILIKE ? "
			+ "OR to_char(start_date, 'HH12:MI:SS') ILIKE ? "
			+ "OR to_char(end_date, 'HH12:MI:SS') ILIKE ?) AND (chief_mentor_id=? OR (user_team.user_id=? AND role.role=?));";
	
	private static final String GET_PROJECT_REPORT_SQL = "SELECT (SELECT COUNT (*) FROM user_team "
			+ "INNER JOIN team ON user_team.team_id=team.id "
			+ "INNER JOIN project ON team.project_id=project.id "
			+ "INNER JOIN user_role ON user_team.user_id=user_role.user_id "
			+ "INNER JOIN role ON user_role.role_id=role.id "
			+ "WHERE project.id = ? AND role.role='student') AS numberOfParticipants, "
			+ "(SELECT COUNT (*) FROM user_team "
			+ "INNER JOIN team ON user_team.team_id=team.id "
			+ "INNER JOIN project ON team.project_id=project.id "
			+ "INNER JOIN user_role ON user_team.user_id=user_role.user_id "
			+ "INNER JOIN role ON user_role.role_id=role.id "
			+ "INNER JOIN status ON user_team.status_id=status.id "
			+ "WHERE project.id = ? AND role.role='student' AND status.name='left_project') AS numberOfParticipantsWhoLeft, "
			+ "(SELECT COUNT (*) FROM user_team "
			+ "INNER JOIN team ON user_team.team_id=team.id "
			+ "INNER JOIN project ON team.project_id=project.id "
			+ "INNER JOIN user_role ON user_team.user_id=user_role.user_id "
			+ "INNER JOIN role ON user_role.role_id=role.id  "
			+ "INNER JOIN status ON user_team.status_id=status.id "
			+ "WHERE project.id = ? AND role.role='student' AND status.name='interview_was_scheduled') AS numberOfParticipantsWhomInterviewWasScheduled, "
			+ "(SELECT COUNT (*) FROM user_team "
			+ "INNER JOIN team ON user_team.team_id=team.id "
			+ "INNER JOIN project ON team.project_id=project.id "
			+ "INNER JOIN user_role ON user_team.user_id=user_role.user_id "
			+ "INNER JOIN role ON user_role.role_id=role.id "
			+ "INNER JOIN status ON user_team.status_id=status.id "
			+ "WHERE project.id = ? AND role.role='student' AND status.name='got_job_offer') AS numberOfParticipantsWhoGotJobOffer;";
	private static final RowMapper<Project> PROJECT_MAPPER = new ProjectMapper();
	private static final RowMapper<ProjectReport> PROJECT_REPORT_MAPPER = new ProjectReportMapper();

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
	public Project getById(Long id) {
		try{
			return jdbcTemplate.queryForObject(GET_PROJECT_BY_ID_SQL,
						new Object[] { id },
						PROJECT_MAPPER);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@Override
	public ProjectReport getProjectReport(Long id){
		try{
			return jdbcTemplate.queryForObject(GET_PROJECT_REPORT_SQL,
						new Object[] { id, id, id, id },
						PROJECT_REPORT_MAPPER);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@Override
	public long countProjectsByChiefMentor(long id) {
		return this.jdbcTemplate.queryForObject(COUNT_PROJECTS_BY_CHIEF_MENTOR_SQL, new Object[] {id}, Long.class);
	}

	@Override
	public long countProjectsByChiefMentor(String searchValue, long id) {
		String searchParam = "%" + searchValue + "%";
		return this.jdbcTemplate.queryForObject(COUNT_PROJECTS_BY_CHIEF_MENTOR_FILTERED_SQL,
				new Object[] { searchParam, searchParam, searchParam, id}, Long.class);
	}

	@Override
	public List<Project> getProjectsByChiefMentor(int offset, int length, int orderBy, SortingOrder order, String searchValue,
			Long id) {
		String query = String.format(GET_PROJECTS_BY_CHIEF_MENTOR_BY_PAGE_SQL,
				OrdableColumn.values()[orderBy].getColumnName(), order);
		String searchParam = "%" + searchValue + "%";
		return jdbcTemplate.query(query,
				new Object[] { searchParam,searchParam, searchParam,  id, length, offset }, PROJECT_MAPPER);
	}
	

	@Override
	public Long countProjectsByMentor(Long id) {
		return this.jdbcTemplate.queryForObject(COUNT_PROJECTS_BY_MENTOR_SQL, new Object[] {id,  id, Role.MENTOR.getName(),}, Long.class);
	}
	
	@Override
	public Long countProjectsByMentor(String searchValue, Long id) {
		String searchParam = "%" + searchValue + "%";
		return this.jdbcTemplate.queryForObject(COUNT_PROJECTS_BY_MENTOR_FILTERED_SQL,
				new Object[] { searchParam, searchParam, searchParam, id, id, Role.MENTOR.getName(),}, Long.class);
	}
	
	@Override
	public List<Project> getProjectsByMentor(int start, int length, int orderBy, SortingOrder order, String searchValue,
			Long id) {
		String query = String.format(GET_PROJECTS_BY_MENTOR_BY_PAGE_SQL,
				OrdableColumn.values()[orderBy].getColumnName(), order);
		String searchParam = "%" + searchValue + "%";
		return jdbcTemplate.query(query,
				new Object[] { searchParam, searchParam, searchParam,  id, id, Role.MENTOR.getName(), length, start }, PROJECT_MAPPER);
	}

	@Override
	public long count() {
		return this.jdbcTemplate.queryForObject(COUNT_PROJECTS_SQL, Long.class);
	}

	@Override
	public long count(String searchValue) {
		String searchParam = "%" + searchValue + "%";
		return this.jdbcTemplate.queryForObject(COUNT_PROJECTS_FILTERED_SQL,
				new Object[] { searchParam, searchParam, searchParam}, Long.class);
	}
	
	@Override
	public List<Project> getProjects(long offset, int length, int orderBy, SortingOrder order, String searchValue) {
		String query = String.format(GET_PROJECTS_BY_PAGE_SQL,
				OrdableColumn.values()[orderBy].getColumnName(), order);
		String searchParam = "%" + searchValue + "%";
		return jdbcTemplate.query(query,
				new Object[] { searchParam, searchParam, searchParam, length, offset }, PROJECT_MAPPER);
	}

	@Override
	public void setChiefUser(User chief, Project project) {
		project.setChiefMentor(chief);
		update(project);
	}

	@Override
	public void deleteChiefUser(Project project) {
		project.setChiefMentor(null);
		update(project);
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
	public void addTraitToProject(Long traitId, Long projectId) {
		jdbcTemplate.update(ADD_TRAIT_TO_PROJECT_SQL, traitId, projectId);
	}	
	@Override
	public void deleteTraitFromProject(Long traitId, Long projectId) {
		jdbcTemplate.update(DELETE_TRAIT_FROM_PROJECT_SQL, traitId, projectId);
	}

	@Override
	public int[] addTraitCategoryToProject(Short traitCategoryId, Long projectId) {
		List<Trait> traits = traitRepository.getTraitsByTraitCategoryAndNotFromProject(traitCategoryId, projectId);
        return jdbcTemplate.batchUpdate(ADD_TRAIT_TO_PROJECT_SQL,
        		prepareTraitsToBanchUpdate(traits, projectId));
	}	

	@Override
	public int[] deleteTraitCategoryFromProject(Short traitCategoryId, Long projectId) {
		List<Trait> traits = traitRepository.getTraitsByTraitCategoryAndProject(traitCategoryId, projectId);
		return jdbcTemplate.batchUpdate(DELETE_TRAIT_FROM_PROJECT_SQL,
        		prepareTraitsToBanchUpdate(traits, projectId));
	}
	
	private List<Object[]> prepareTraitsToBanchUpdate(List<Trait> traits, Long projectId){

		List<Object[]> batch = new ArrayList<Object[]>();
        for (Trait trait : traits) {
            Object[] values = new Object[] {
            		trait.getId(),
                    projectId};
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
	
	private static final class ProjectReportMapper implements RowMapper<ProjectReport> {
		@Override
		public ProjectReport mapRow(ResultSet rs, int rowNum) throws SQLException {
			ProjectReport pr = new ProjectReport();
			pr.setNumberOfParticipants(rs.getInt("numberOfParticipants"));
			pr.setNumberOfParticipantsWhoGotJobOffer(rs.getInt("numberOfParticipantsWhoGotJobOffer"));
			pr.setNumberOfParticipantsWhoLeft(rs.getInt("numberOfParticipantsWhoLeft"));
			pr.setNumberOfParticipantsWhomInterviewWasScheduled(rs.getInt("numberOfParticipantsWhomInterviewWasScheduled"));
			return pr;
		}
	}
	
	private static enum OrdableColumn {
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
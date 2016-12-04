package nc.ukma.thor.spms.repository.jdbcImpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.entity.Status;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.entity.UserStatus;
import nc.ukma.thor.spms.entity.report.StudentReport;
import nc.ukma.thor.spms.repository.UserRepository;
import nc.ukma.thor.spms.util.SortingOrder;

@Repository
public class UserRepositoryJdbcImpl implements UserRepository {

	private static final String GET_USER_BY_ID_SQL = "SELECT * FROM \"user\" "
			+ "LEFT JOIN application_form ON \"user\".id = application_form.user_id "
			+ "INNER JOIN user_role ON \"user\".id = user_role.user_id "
			+ "INNER JOIN role ON role.id = user_role.role_id " + "WHERE \"user\".id = ?;";

	private static final String GET_USER_BY_EMAIL_SQL = "SELECT * FROM \"user\" "
			+ "LEFT JOIN application_form ON \"user\".id = application_form.user_id "
			+ "INNER JOIN user_role ON \"user\".id = user_role.user_id "
			+ "INNER JOIN role ON role.id = user_role.role_id " + "WHERE email = ?;";

	private static final String GET_USERS_BY_TEAM_SQL = "SELECT * FROM \"user\" "
			+ "LEFT JOIN application_form ON \"user\".id = application_form.user_id "
			+ "INNER JOIN user_role ON \"user\".id = user_role.user_id "
			+ "INNER JOIN user_team ON \"user\".id = user_team.user_id "
			+ "INNER JOIN role ON role.id = user_role.role_id " + "WHERE team_id = ?;";
	
	private static final String GET_MENTORS_BY_TEAM_SQL = "SELECT * FROM \"user\" "
			+ "LEFT JOIN application_form ON \"user\".id = application_form.user_id "
			+ "INNER JOIN user_role ON \"user\".id = user_role.user_id "
			+ "INNER JOIN user_team ON \"user\".id = user_team.user_id "
			+ "INNER JOIN role ON role.id = user_role.role_id "
			+ "WHERE team_id = ? AND role='mentor';";
	
	private static final String GET_CHIEF_MENTOR_BY_PROJECT_SQL = "SELECT * FROM \"user\" "
			+ "LEFT JOIN application_form ON \"user\".id = application_form.user_id "
			+ "INNER JOIN user_role ON \"user\".id = user_role.user_id "
			+ "INNER JOIN project ON \"user\".id = project.chief_mentor_id "
			+ "INNER JOIN role ON role.id = user_role.role_id "
			+ "WHERE project.id = ?;";
	
	private static final String GET_STUDENTS_BY_TEAM_SQL = "SELECT "
			+ "\"user\".id AS user_id, "
			+ "\"user\".email AS user_email, \"user\".first_name AS user_first_name, "
			+ "\"user\".second_name AS user_second_name, \"user\".last_name AS user_last_name, "
			+ "\"user\".is_active AS user_is_active, "
			+ "user_team.comment as user_team_comment, "
			+ "application_form.photo_scope AS application_form_photo_scope, "
			+ "status.id AS status_id, status.name AS status_name "
			+ "FROM \"user\" "
			+ "LEFT JOIN application_form ON \"user\".id = application_form.user_id "
			+ "INNER JOIN user_role ON \"user\".id = user_role.user_id "
			+ "INNER JOIN role ON role.id = user_role.role_id "
			+ "INNER JOIN user_team ON \"user\".id = user_team.user_id "
			+ "INNER JOIN status ON user_team.status_id = status.id "
			+ "WHERE team_id = ? AND role = 'student';";

	private static final String GET_USERS_BY_MEETING_SQL = "SELECT * FROM \"user\" "
			+ "LEFT JOIN application_form ON \"user\".id = application_form.user_id "
			+ "INNER JOIN user_role ON \"user\".id = user_role.user_id "
			+ "INNER JOIN role ON role.id = user_role.role_id "
			+ "INNER JOIN presence ON \"user\".id = presence.user_id " + "WHERE meeting_id = ?;";

	private static final String GET_ALL_USERS_SQL = "SELECT * FROM \"user\" "
			+ "LEFT JOIN application_form ON \"user\".id = application_form.user_id "
			+ "INNER JOIN user_role ON \"user\".id = user_role.user_id "
			+ "INNER JOIN role ON role.id = user_role.role_id;";
	/*ALL USERS PAGINATION*/
	private static final String GET_USERS_BY_PAGE_SQL = "SELECT * FROM \"user\" "
			+ "LEFT JOIN application_form ON \"user\".id = application_form.user_id "
			+ "INNER JOIN user_role ON \"user\".id = user_role.user_id "
			+ "INNER JOIN role ON role.id = user_role.role_id " 
			+ "WHERE email ILIKE ? OR first_name ILIKE ? OR second_name ILIKE ? "
			+ "OR last_name ILIKE ? OR role ILIKE ? " 
			+ "ORDER BY \"user\".%s %s, \"user\".id "//default ordering by user id, it is important for pagination
			+ "LIMIT ? OFFSET ?;";

	private static final String COUNT_USERS_SQL = "SELECT COUNT(*) FROM \"user\";";
	/*This query is so huge, because we have to consider search on user role*/
	private static final String COUNT_USERS_FILTERED_SQL = "SELECT COUNT(*) FROM \"user\" "
			+ "LEFT JOIN application_form ON \"user\".id = application_form.user_id "
			+ "INNER JOIN user_role ON \"user\".id = user_role.user_id "
			+ "INNER JOIN role ON role.id = user_role.role_id " 
			+ "WHERE email ILIKE ? OR first_name ILIKE ? OR second_name ILIKE ? "
			+ "OR last_name ILIKE ? OR role ILIKE ?;";
	
	/*ALL USERS BY ROLE PAGINATION*/
	private static final String GET_USERS_BY_ROLE_BY_PAGE_SQL = "SELECT * FROM \"user\" "
			+ "LEFT JOIN application_form ON \"user\".id = application_form.user_id "
			+ "INNER JOIN user_role ON \"user\".id = user_role.user_id "
			+ "INNER JOIN role ON role.id = user_role.role_id " 
			+ "WHERE (email ILIKE ? OR first_name ILIKE ? OR second_name ILIKE ? "
			+ "OR last_name ILIKE ?) AND role=? " 
			+ "ORDER BY \"user\".%s %s, \"user\".id "//default ordering by user id, it is important for pagination
			+ "LIMIT ? OFFSET ?;";
	private static final String COUNT_USERS_BY_ROLE_SQL = "SELECT COUNT(*) FROM \"user\" "
			+ "LEFT JOIN application_form ON \"user\".id = application_form.user_id "
			+ "INNER JOIN user_role ON \"user\".id = user_role.user_id "
			+ "INNER JOIN role ON role.id = user_role.role_id "
			+ "WHERE role=?;";
	private static final String COUNT_USERS_BY_ROLE_FILTERED_SQL = "SELECT COUNT(*) FROM \"user\" "
			+ "LEFT JOIN application_form ON \"user\".id = application_form.user_id "
			+ "INNER JOIN user_role ON \"user\".id = user_role.user_id "
			+ "INNER JOIN role ON role.id = user_role.role_id " 
			+ "WHERE (email ILIKE ? OR first_name ILIKE ? OR second_name ILIKE ? "
			+ "OR last_name ILIKE ?) AND role=?;";

	/*FREE USERS BY ROLE PAGINATION*/
	private static final String GET_FREE_USERS_BY_ROLE_BY_PAGE_SQL = "SELECT * "//DISTINCT \"user\".id, "
		//	+ "email, first_name, second_name, last_name, password, is_active, role, role_id, photo_scope "
			+ "FROM \"user\" AS X "
			+ "LEFT JOIN application_form ON X.id = application_form.user_id "
			+ "INNER JOIN user_role ON X.id = user_role.user_id "
			+ "INNER JOIN role ON role.id = user_role.role_id " 
			+ "WHERE (email ILIKE ? OR first_name ILIKE ? OR second_name ILIKE ? "
			+ "OR last_name ILIKE ?) AND role=? AND "
			+ "NOT EXISTS (SELECT * FROM user_team "
						+ "INNER JOIN team ON user_team.team_id=team.id "
						+ "INNER JOIN project ON team.project_id=project.id "
						+ "WHERE project.is_completed=FALSE AND user_team.user_id=X.id) " 
			+ "ORDER BY X.%s %s, X.id "//default ordering by user id, it is important for pagination
			+ "LIMIT ? OFFSET ?;";
	private static final String COUNT_FREE_USERS_BY_ROLE_SQL = "SELECT COUNT(*) FROM \"user\" AS X "
			+ "LEFT JOIN application_form ON X.id = application_form.user_id "
			+ "INNER JOIN user_role ON X.id = user_role.user_id "
			+ "INNER JOIN role ON role.id = user_role.role_id "
			+ "WHERE role=? AND "
			+ "NOT EXISTS (SELECT * FROM user_team "
						+ "INNER JOIN team ON user_team.team_id=team.id "
						+ "INNER JOIN project ON team.project_id=project.id "
						+ "WHERE project.is_completed=FALSE AND user_team.user_id=X.id);";
	private static final String COUNT_FREE_USERS_BY_ROLE_FILTERED_SQL = "SELECT COUNT(*) FROM \"user\" AS X "
			+ "LEFT JOIN application_form ON X.id = application_form.user_id "
			+ "INNER JOIN user_role ON X.id = user_role.user_id "
			+ "INNER JOIN role ON role.id = user_role.role_id " 
			+ "WHERE (email ILIKE ? OR first_name ILIKE ? OR second_name ILIKE ? "
			+ "OR last_name ILIKE ?) AND role=? AND "
			+ "NOT EXISTS (SELECT * FROM user_team "
						+ "INNER JOIN team ON user_team.team_id=team.id "
						+ "INNER JOIN project ON team.project_id=project.id "
						+ "WHERE project.is_completed=FALSE AND user_team.user_id=X.id);";

	private static final String IS_USER_MEMBER_OF_PROJECT_SQL = "SELECT EXISTS(SELECT * FROM user_team "
			+ "INNER JOIN team ON user_team.team_id = team.id "
			+ "WHERE user_team.user_id=? AND team.project_id=?);";
		
	private static final String IS_USER_MEMBER_OF_TEAM_SQL = "SELECT EXISTS(SELECT * FROM user_team "
			+ "WHERE user_team.user_id=? AND user_team.team_id=?);";
	
	private static final String IS_USER_MEMBER_OF_TEAM_WITH_MEETING_SQL = "SELECT EXISTS(SELECT * FROM user_team "
			+ "INNER JOIN team ON user_team.team_id=team.id "
			+ "INNER JOIN meeting ON team.id=meeting.team_id "
			+ "WHERE user_team.user_id=? AND meeting.id=?);";
	private static final String IS_USER_MEMBER_OF_TEAM_WITH_MEETING_FEEDBACK_SQL  = "SELECT EXISTS(SELECT * FROM user_team "
			+ "INNER JOIN team ON user_team.team_id=team.id "
			+ "INNER JOIN meeting ON team.id=meeting.team_id "
			+ "INNER JOIN meeting_feedback ON meeting.id=meeting_feedback.meeting_id "
			+ "WHERE user_team.user_id=? AND meeting_feedback.id=?);";
	private static final String IS_USER_MEMBER_OF_TEAM_WITH_MEMBER_SQL = "SELECT EXISTS(SELECT * FROM user_team AS X "
			+ "WHERE user_id=? AND EXISTS(SELECT * FROM user_team "
									   + "WHERE team_id=X.team_id AND user_id=?));";
	
	private static final String IS_USER_CHIEF_MENTOR_SQL = "SELECT EXISTS(SELECT * FROM project "
			+ "WHERE chief_mentor_id=?);";
	private static final String IS_USER_CHIEF_MENTOR_OF_PROJECT_SQL = "SELECT EXISTS(SELECT * FROM project "
			+ "WHERE chief_mentor_id=? AND id=?);";
	private static final String IS_USER_CHIEF_MENTOR_OF_PROJECT_WITH_TEAM_SQL = "SELECT EXISTS(SELECT * FROM project "
			+ "INNER JOIN team ON project.id=team.project_id "
			+ "WHERE chief_mentor_id=? AND team.id=?);";
	private static final String IS_USER_CHIEF_MENTOR_OF_PROJECT_WITH_MEETING_SQL = "SELECT EXISTS(SELECT * FROM project "
			+ "INNER JOIN team ON project.id=team.project_id "
			+ "INNER JOIN meeting ON team.id=meeting.team_id "
			+ "WHERE chief_mentor_id=? AND meeting.id=?);";
	private static final String IS_USER_CHIEF_MENTOR_OF_PROJECT_WITH_MEETING_FEEDBACK_SQL = "SELECT EXISTS(SELECT * FROM project "
			+ "INNER JOIN team ON project.id=team.project_id "
			+ "INNER JOIN meeting ON team.id=meeting.team_id "
			+ "INNER JOIN meeting_feedback ON meeting.id=meeting_feedback.meeting_id "
			+ "WHERE chief_mentor_id=? AND meeting_feedback.id=?);";
	private static final String IS_USER_CHIEF_MENTOR_OF_PROJECT_WITH_MEMBER_SQL = "SELECT EXISTS(SELECT * FROM project "
			+ "INNER JOIN team ON project.id=team.project_id "
			+ "INNER JOIN user_team ON team.id=user_team.team_id "
			+ "WHERE chief_mentor_id=? AND user_team.user_id=?);";


	private static final String CHANGE_USER_STATUS_SQL = "UPDATE user_team "
			+ "SET status_id = ?, comment = ? "
			+ "WHERE team_id = ? AND user_id = ?";
	
	private static final RowMapper<User> USER_MAPPER = new UserMapper();
	private static final TeamStudentsExtractor TEAM_STUDENTS_EXTRACTOR = new TeamStudentsExtractor();

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public User getUserById(Long id) {
		try {
			User user = jdbcTemplate.queryForObject(GET_USER_BY_ID_SQL, new Object[] { id }, USER_MAPPER);
			return user;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public User getUserByEmail(String email) {
		try {
			User user = jdbcTemplate.queryForObject(GET_USER_BY_EMAIL_SQL, new Object[] { email }, USER_MAPPER);
			return user;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}

	@Override
	public List<User> getUsersByTeam(Team team) {
		return jdbcTemplate.query(GET_USERS_BY_TEAM_SQL, new Object[] { team.getId() }, USER_MAPPER);
	}
	
	@Override
	public List<User> getMentorsByTeam(Team team) {
		return jdbcTemplate.query(GET_MENTORS_BY_TEAM_SQL, new Object[] { team.getId() }, USER_MAPPER);
	}
	
	@Override
	public User getChiefMentorByProject(long projectId) {
		try {
			User user = jdbcTemplate.queryForObject(GET_CHIEF_MENTOR_BY_PROJECT_SQL, new Object[] { projectId }, USER_MAPPER);
			return user;
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public HashMap<User, UserStatus> getStudentsByTeam(Team team) {
		List<HashMap<User, UserStatus>> students = jdbcTemplate.query(GET_STUDENTS_BY_TEAM_SQL, new Object[]{team.getId()}, TEAM_STUDENTS_EXTRACTOR);
		if(students.isEmpty()) return null;
		else return students.get(0);
	}

	@Override
	public List<User> getAllUsers() {
		return jdbcTemplate.query(GET_ALL_USERS_SQL, USER_MAPPER);
	}

	@Override
	public List<User> getUsersPresentAtMeeting(Meeting meeting) {
		return jdbcTemplate.query(GET_USERS_BY_MEETING_SQL, new Object[] { meeting.getId() }, USER_MAPPER);
	}

	public List<User> getUsers(long offset, int length, int orderBy, SortingOrder order, String searchString) {
		String query = String.format(GET_USERS_BY_PAGE_SQL,
				OrderableColumn.values()[orderBy].getColumnName(),
				order);
		String searchParam = "%" + searchString + "%";
		return jdbcTemplate.query(query,
				new Object[] { searchParam,searchParam,searchParam, searchParam, searchParam, length, offset }, USER_MAPPER);
	}
	
	@Override
	public long count() {
		return this.jdbcTemplate.queryForObject(COUNT_USERS_SQL, Long.class);
	}
	
	@Override
	public long countFiltered(String searchString) {
		String searchParam = "%" + searchString + "%";
		return this.jdbcTemplate.queryForObject(COUNT_USERS_FILTERED_SQL,
				new Object[] { searchParam, searchParam, searchParam, searchParam, searchParam}, Long.class);
	}
	
	@Override
	public List<User> getUsersByRole(long offset, int length, int orderBy,
									 SortingOrder order, String searchString, Role role) {
		String query = String.format(GET_USERS_BY_ROLE_BY_PAGE_SQL,
				OrderableColumn.values()[orderBy].getColumnName(),
				order);
		String searchParam = "%" + searchString + "%";
		return jdbcTemplate.query(query,
				new Object[] { searchParam,searchParam,searchParam, searchParam, role.getName(), length, offset }, USER_MAPPER);
	}
	
	@Override
	public long countUsersByRole(Role role) {
		return this.jdbcTemplate.queryForObject(COUNT_USERS_BY_ROLE_SQL,
				new Object[] { role.getName()}, Long.class);
	}

	@Override
	public long countUsersByRoleFiltered(Role role, String searchString) {
		String searchParam = "%" + searchString + "%";
		return this.jdbcTemplate.queryForObject(COUNT_USERS_BY_ROLE_FILTERED_SQL,
				new Object[] { searchParam, searchParam, searchParam, searchParam, role.getName()}, Long.class);
	}

	@Override
	public List<User> getFreeUsersByRole(long offset, int length, int orderBy, SortingOrder order, String searchString,
			Role role) {
		String query = String.format(GET_FREE_USERS_BY_ROLE_BY_PAGE_SQL,
				OrderableColumn.values()[orderBy].getColumnName(),
				order);
		String searchParam = "%" + searchString + "%";
		return jdbcTemplate.query(query,
				new Object[] { searchParam, searchParam, searchParam, searchParam, role.getName(), length, offset }, USER_MAPPER);
	}

	@Override
	public long countFreeUsersByRole(Role role) {
		return this.jdbcTemplate.queryForObject(COUNT_FREE_USERS_BY_ROLE_SQL,
				new Object[] { role.getName()}, Long.class);
	}

	@Override
	public long countFreeUsersByRoleFiltered(Role role, String searchString) {
		String searchParam = "%" + searchString + "%";
		return this.jdbcTemplate.queryForObject(COUNT_FREE_USERS_BY_ROLE_FILTERED_SQL,
				new Object[] { searchParam, searchParam, searchParam, searchParam, role.getName()}, Long.class);
	}
	
	@Override
	public boolean isUserMemberOfProject(long id, long projectId) {
		return jdbcTemplate.queryForObject(IS_USER_MEMBER_OF_PROJECT_SQL, new Object[] {id, projectId}, Boolean.class);
	}
	
	@Override
	public boolean isUserMemberOfTeam(long id, long teamId) {
		return jdbcTemplate.queryForObject(IS_USER_MEMBER_OF_TEAM_SQL, new Object[] {id, teamId}, Boolean.class);
	}
	@Override
	public boolean isUserMemberOfTeamWithMeeting(long id, long meetingId) {
		return jdbcTemplate.queryForObject(IS_USER_MEMBER_OF_TEAM_WITH_MEETING_SQL, new Object[] {id, meetingId}, Boolean.class);
	}

	@Override
	public boolean isUserMemberOfTeamWithMeetingFeedback(long id, long meetingFeedbackId) {
		return jdbcTemplate.queryForObject(IS_USER_MEMBER_OF_TEAM_WITH_MEETING_FEEDBACK_SQL, new Object[] {id, meetingFeedbackId}, Boolean.class);
	}

	@Override
	public boolean isUserMemberOfTeamWithMember(long id, long userId) {
		return jdbcTemplate.queryForObject(IS_USER_MEMBER_OF_TEAM_WITH_MEMBER_SQL, new Object[] {id, userId}, Boolean.class);
	}
	
	@Override
	public boolean isUserChiefMentor(long id) {
		return jdbcTemplate.queryForObject(IS_USER_CHIEF_MENTOR_SQL, new Object[] {id}, Boolean.class);
	}
	
	@Override
	public boolean isUserChiefMentorOfProject(long id, long projectId) {
		return jdbcTemplate.queryForObject(IS_USER_CHIEF_MENTOR_OF_PROJECT_SQL, new Object[] {id, projectId}, Boolean.class);
	}

	@Override
	public boolean isUserChiefMentorOfProjectWithTeam(long id, long teamId) {
		return jdbcTemplate.queryForObject(IS_USER_CHIEF_MENTOR_OF_PROJECT_WITH_TEAM_SQL, new Object[] {id, teamId}, Boolean.class);
	}

	@Override
	public boolean isUserChiefMentorOfProjectWithMeeting(long id, long meetingId) {
		return jdbcTemplate.queryForObject(IS_USER_CHIEF_MENTOR_OF_PROJECT_WITH_MEETING_SQL, new Object[] {id, meetingId}, Boolean.class);
	}

	@Override
	public boolean isUserChiefMentorOfProjectWithMeetingFeedback(long id, long meetingFeedbackId) {
		return jdbcTemplate.queryForObject(IS_USER_CHIEF_MENTOR_OF_PROJECT_WITH_MEETING_FEEDBACK_SQL, new Object[] {id, meetingFeedbackId}, Boolean.class);
	}

	@Override
	public boolean isUserChiefMentorOfProjectWithMember(long id, long userId) {
		return jdbcTemplate.queryForObject(IS_USER_CHIEF_MENTOR_OF_PROJECT_WITH_MEMBER_SQL, new Object[] {id, userId}, Boolean.class);
	}
	
	
	public static enum OrderableColumn {
		ID("id"), EMAIL("email"), FIRST_NAME("first_name"), SECOND_NAME("second_name"), LAST_NAME("last_name");

		private String columnName;

		private OrderableColumn(String columnName) {
			this.columnName = columnName;
		}
		public String getColumnName(){
			return columnName;
		}
	}
	
	private static final class UserMapper implements RowMapper<User> {
		@Override
		public User mapRow(ResultSet rs, int rowNum) throws SQLException {
			User user = new User();
			user.setId(rs.getLong("id"));
			user.setEmail(rs.getString("email"));
			user.setFirstName(rs.getString("first_name"));
			user.setSecondName(rs.getString("second_name"));
			user.setLastName(rs.getString("last_name"));
			user.setPassword(rs.getString("password"));
			user.setActive(rs.getBoolean("is_active"));
			
			Role role = Role.valueOf(rs.getString("role").toUpperCase());
			role.setId(rs.getShort("role_id"));
			user.setRole(role);
			
			String linkToPhoto = rs.getString("photo_scope");
			if (!rs.wasNull())
				user.setLinkToPhoto(linkToPhoto);
			return user;
		}
	}
	
	@Override
	public void changeUserStatus(long team_id, long user_id, long new_status, String new_comment) {
		Object [] values = {
				new_status,
				new_comment,
				team_id,
				user_id
		};
		jdbcTemplate.update(CHANGE_USER_STATUS_SQL, values);
	}
	
	// only for one team!!!
	private static final class TeamStudentsExtractor implements ResultSetExtractor<List<HashMap<User, UserStatus>>>{
		@Override
		public List<HashMap<User, UserStatus>> extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			List<HashMap<User, UserStatus>> result = new ArrayList<HashMap<User, UserStatus>>();
			HashMap<User, UserStatus> students = new HashMap<User, UserStatus>();
			
			while(rs.next()){

				User user = new User();
				user.setId(rs.getLong("user_id"));
				user.setEmail(rs.getString("user_email"));
				user.setFirstName(rs.getString("user_first_name"));
				user.setSecondName(rs.getString("user_second_name"));
				user.setLastName(rs.getString("user_last_name"));
				user.setActive(rs.getBoolean("user_is_active"));
				user.setLinkToPhoto(rs.getString("application_form_photo_scope"));

				UserStatus userStatus = null;
				Status status = Status.valueOf(rs.getString("status_name").toUpperCase());
				status.setId(rs.getShort("status_id"));
				userStatus = new UserStatus(status);
				userStatus.setComment(rs.getString("user_team_comment"));
					
				students.put(user, userStatus);				
			}
			
			result.add(students);
			
			return result;
		}
	}

	public StudentReport getStudentReport(long studentId, long projectId) {
		// TODO Auto-generated method stub
		return null;
	}


}

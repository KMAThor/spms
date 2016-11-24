package nc.ukma.thor.spms.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import nc.ukma.thor.spms.entity.File;
import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.entity.Status;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.entity.UserStatus;

@Repository
public class TeamRepositoryJdbcImpl implements TeamRepository{
	
	private static final String INSERT_TEAM_SQL = "INSERT INTO team (name, project_id) VALUES(?,?);";
	private static final String UPDATE_TEAM_SQL = "UPDATE team SET name=? WHERE id = ?;";
	private static final String DELETE_TEAM_SQL = "DELETE FROM team WHERE id = ?;";
	private static final String GET_TEAM_BY_ID_SQL = "SELECT * FROM team WHERE id = ?;";
	private static final String GET_FULL_TEAM_BY_ID_SQL = "SELECT team.id AS team_id, "
			+ "team.name AS team_name, team.project_id AS team_project_id, "
			+ "project.is_completed AS project_is_completed, file.id AS file_id, file.path AS file_path, "
			+ "meeting.id AS meeting_id, meeting.topic AS meeting_topic, "
			+ "meeting.start_date AS meeting_start_date, \"user\".id AS user_id, "
			+ "\"user\".email AS user_email, \"user\".first_name AS user_first_name, "
			+ "\"user\".second_name AS user_second_name, \"user\".last_name AS user_last_name, "
			+ "\"user\".is_active AS user_is_active, "
			+ "application_form.photo_scope AS application_form_photo_scope, "
			+ "status.id AS status_id, status.name AS status_name, "
			+ "role.id AS role_id, role.role AS role_role "
			+ "FROM team "
			+ "INNER JOIN project ON team.project_id = project.id "
			+ "LEFT JOIN file ON team.id = file.team_id "
			+ "LEFT JOIN meeting ON team.id = meeting.team_id "
			+ "LEFT JOIN user_team ON team.id = user_team.team_id "
			+ "LEFT JOIN \"user\" ON user_team.user_id = \"user\".id "
			+ "LEFT JOIN status ON user_team.status_id = status.id "
			+ "LEFT JOIN application_form ON \"user\".id = application_form.user_id "
			+ "LEFT JOIN user_role ON \"user\".id = user_role.user_id "
			+ "LEFT JOIN role ON user_role.role_id = role.id "
			+ "WHERE team.id = ?;";
	private static final String GET_TEAMS_BY_USER_SQL = "SELECT * FROM team "
			+ "INNER JOIN user_team ON team.id = user_team.team_id "
			+ "WHERE user_team.user_id = ?;";
	private static final String GET_TEAMS_BY_PROJECT_SQL = "SELECT * FROM team WHERE project_id = ?;";
	private static final String ADD_USER_TO_TEAM_SQL = "INSERT INTO user_team (user_id, team_id) VALUES (?,?);";
	private static final String DELETE_USER_FROM_TEAM_SQL = "DELETE FROM user_team WHERE user_id=? AND team_id=?;";
	
	private static final String GET_ACTIVE_TEAM_BY_USER_SQL = "SELECT * FROM team "
			+ "INNER JOIN user_team ON team.id = user_team.team_id "
			+ "INNER JOIN project ON team.project_id=project.id "
			+ "WHERE user_team.user_id = ? AND project.is_completed=FALSE;";
	private static final String GET_USER_STATUS_IN_TEAM_SQL = "SELECT * FROM user_team "
			+ "INNER JOIN status ON user_team.status_id = status.id "
			+ "WHERE user_id=? AND team_id=?;";
	private static final String CHANGE_USER_STATUS_IN_TEAM_SQL = "UPDATE user_team "
			+ "SET status_id=(SELECT id FROM status WHERE name=?), comment=? "
			+ "WHERE user_id=? AND team_id=?;";
	
	
	private static final RowMapper<Team> TEAM_MAPPER = new TeamMapper();
	private static final RowMapper<UserStatus> STATUS_MAPPER = new UserStatusMapper();
	private static final TeamExtractor TEAM_EXTRACTOR = new TeamExtractor();

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void add(Team t) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(INSERT_TEAM_SQL, new String[] {"id"});
			ps.setString(1, t.getName());
			ps.setLong(2, t.getProject().getId());
			return ps;
		}, keyHolder);
		t.setId(keyHolder.getKey().longValue());
	}

	@Override
	public void update(Team t) {
		jdbcTemplate.update(UPDATE_TEAM_SQL, new Object[] {t.getName(), t.getId()});
	}

	@Override
	public void delete(Team t) {
		jdbcTemplate.update(DELETE_TEAM_SQL, t.getId());
	}

	@Override
	public Team getById(Long id) {
		try{
			return jdbcTemplate.queryForObject(GET_TEAM_BY_ID_SQL,
					new Object[] {id}, TEAM_MAPPER);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@Override
	public Team getActiveTeamByUser(Long id) {
		try{
			return jdbcTemplate.queryForObject(GET_ACTIVE_TEAM_BY_USER_SQL,
					new Object[] {id}, TEAM_MAPPER);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@Override
	public List<Team> getTeamsByUser(Long userId) {
		return jdbcTemplate.query(GET_TEAMS_BY_USER_SQL,
				new Object[] { userId }, TEAM_MAPPER);
	}

	@Override
	public List<Team> getTeamsByProject(Long projectId) {
		return jdbcTemplate.query(GET_TEAMS_BY_PROJECT_SQL,
					new Object[] { projectId }, TEAM_MAPPER);
	}
	
	@Override
	public void addUserToTeam(Long userId, Long teamId) {
		jdbcTemplate.update(ADD_USER_TO_TEAM_SQL, userId, teamId);
	}

	@Override
	public void deleteUserFromTeam(Long userId, Long teamId) {
		jdbcTemplate.update(DELETE_USER_FROM_TEAM_SQL, userId, teamId);
	}
	
	@Override
	public UserStatus getUserStatusInTeam(Long userId, Long teamId) {
		try{
			return jdbcTemplate.queryForObject(GET_USER_STATUS_IN_TEAM_SQL, new Object[]{userId, teamId},
					STATUS_MAPPER);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@Override
	public Team getFullTeamById(Long teamId) {
		List<Team> teams = jdbcTemplate.query(GET_FULL_TEAM_BY_ID_SQL, new Object[]{teamId}, TEAM_EXTRACTOR);
		if(teams.isEmpty()) return null;
		else return teams.get(0);
	}

	@Override
	public void changeUserStatusInTeam(Long userId, Long teamId, UserStatus userStatus) {
		jdbcTemplate.update(CHANGE_USER_STATUS_IN_TEAM_SQL,
				new Object[]{ userStatus.getStatus().getName(), userStatus.getComment(), userId, teamId});
	}
	
	private static final class TeamMapper implements RowMapper<Team>{
		@Override
		public Team mapRow(ResultSet rs, int rowNum) throws SQLException {
			Team team = new Team();
			team.setId(rs.getLong("id"));
			team.setName(rs.getString("name"));
			team.setProject(new Project(rs.getLong("project_id")));
			return team;
		}
	}
	
	private static final class UserStatusMapper implements RowMapper<UserStatus>{
		@Override
		public UserStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
			Status status = Status.valueOf(rs.getString("name").toUpperCase());
			status.setId(rs.getShort("status_id"));
			
			UserStatus userStatus = new UserStatus(status, rs.getString("comment"));
			return userStatus;
		}
	}
	
	//only for one team!
	private static final class TeamExtractor implements ResultSetExtractor<List<Team>>{
		@Override
		public List<Team> extractData(ResultSet rs) throws SQLException, DataAccessException {
			
			List<Team> teams = new ArrayList<Team>();
			
			Team team = null;

			List<File> files = new ArrayList<File>();
			List<Meeting> meetings = new ArrayList<Meeting>();
			Map<User, UserStatus> users = new HashMap<User, UserStatus>();
			
			while(rs.next()){
				if (team == null) {
					team = new Team();
					
					//Team information
					team.setId(rs.getLong("team_id"));
					team.setName(rs.getString("team_name"));
					
					//Team project
					Project project = new Project();
					project.setId(rs.getLong("team_project_id"));
					project.setIsCompleted(rs.getBoolean("project_is_completed"));
					team.setProject(project);
				}
				
				File file = new File();
				file.setId(rs.getLong("file_id"));
				if (!files.contains(file) && (rs.getLong("file_id") != 0)){
					file.setPath(rs.getString("file_path"));
					
					files.add(file);
				}
				
				//user
				User user = new User();
				user.setId(rs.getLong("user_id"));
				if (!users.containsKey(user) && (rs.getLong("user_id") != 0)){
					user.setEmail(rs.getString("user_email"));
					user.setFirstName(rs.getString("user_first_name"));
					user.setSecondName(rs.getString("user_second_name"));
					user.setLastName(rs.getString("user_last_name"));
					user.setActive(rs.getBoolean("user_is_active"));
					user.setLinkToPhoto(rs.getString("application_form_photo_scope"));
					
					Role role = Role.valueOf(rs.getString("role_role").toUpperCase());
					role.setId(rs.getShort("role_id"));
					user.setRole(role);
					
					UserStatus userStatus = null;
					if (rs.getShort("status_id") != 0){
						Status status = Status.valueOf(rs.getString("status_name").toUpperCase());
						status.setId(rs.getShort("status_id"));
						userStatus = new UserStatus(status);
					}
					
					users.put(user, userStatus);
				}
				
				//meeting
				Meeting meeting = new Meeting();
				meeting.setId(rs.getLong("meeting_id"));
				if (!meetings.contains(meeting) && (rs.getLong("meeting_id") != 0)){
					meeting.setTopic(rs.getString("meeting_topic"));
					meeting.setStartDate(rs.getTimestamp("meeting_start_date"));
					
					meetings.add(meeting);
				}
				
			}
			
			team.setFiles(files);
			team.setMeetings(meetings);
			team.setMembers(users);
			teams.add(team);
			return teams;
		}
	}
	
}

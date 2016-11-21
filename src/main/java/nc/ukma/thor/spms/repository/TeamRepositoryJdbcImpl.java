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
import nc.ukma.thor.spms.entity.Status;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.UserStatus;

@Repository
public class TeamRepositoryJdbcImpl implements TeamRepository{
	
	private static final String INSERT_TEAM_SQL = "INSERT INTO team (name, project_id) VALUES(?,?);";
	private static final String UPDATE_TEAM_SQL = "UPDATE team SET name=? WHERE id = ?;";
	private static final String DELETE_TEAM_SQL = "DELETE FROM team WHERE id = ?;";
	private static final String GET_TEAM_BY_ID_SQL = "SELECT * FROM team WHERE id = ?;";
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
}

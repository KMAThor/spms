package nc.ukma.thor.spms.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;

@Repository
public class UserRepositoryJdbcImpl implements UserRepository{
	
	private static final String GET_USER_BY_ID_SQL = "SELECT * FROM \"user\" "
			+ "LEFT JOIN application_form ON \"user\".id = application_form.user_id "
			+ "INNER JOIN user_role ON \"user\".id = user_role.user_id "
			+ "INNER JOIN role ON role.id = user_role.role_id "
			+ "WHERE \"user\".id = ?";
	
	private static final String GET_USER_BY_EMAIL_SQL = "SELECT * FROM \"user\" "
			+ "LEFT JOIN application_form ON \"user\".id = application_form.user_id "
			+ "INNER JOIN user_role ON \"user\".id = user_role.user_id "
			+ "INNER JOIN role ON role.id = user_role.role_id "
			+ "WHERE email = ?";

	private static final String GET_USERS_BY_TEAM_SQL = "SELECT * FROM \"user\" "
			+ "LEFT JOIN application_form ON \"user\".id = application_form.user_id "
			+ "INNER JOIN user_role ON \"user\".id = user_role.user_id "
			+ "INNER JOIN user_team ON \"user\".id = user_team.user_id "
			+ "INNER JOIN role ON role.id = user_role.role_id "
			+ "WHERE team_id = ?";
	
	private static final String GET_USERS_BY_MEETING_SQL = "SELECT * FROM \"user\" "
			+ "LEFT JOIN application_form ON \"user\".id = application_form.user_id "
			+ "INNER JOIN user_role ON \"user\".id = user_role.user_id "
			+ "INNER JOIN role ON role.id = user_role.role_id "
			+ "INNER JOIN presence ON \"user\".id = presence.user_id "
			+ "WHERE meeting_id = ?";
	
	private static final RowMapper<User> USER_MAPPER = new UserMapper();
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public User getUserById(long id) {
		try{
			User user = jdbcTemplate.queryForObject(GET_USER_BY_ID_SQL,
					new Object[]{ id }, USER_MAPPER);
			return user;
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@Override
	public User getUserByEmail(String email) {
		try{
			User user = jdbcTemplate.queryForObject(GET_USER_BY_EMAIL_SQL,
					new Object[]{ email }, USER_MAPPER);
			return user;
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}

	@Override
	public List<User> getUsersByTeam(Team team) {
		return jdbcTemplate.query(GET_USERS_BY_TEAM_SQL,
				new Object[]{ team.getId() }, USER_MAPPER);
	}
	
	@Override
	public List<User> getUsersPresentAtMeeting(Meeting meeting) {
		return jdbcTemplate.query(GET_USERS_BY_MEETING_SQL,
				new Object[]{ meeting.getId() }, USER_MAPPER);
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
			user.setRole(new Role(rs.getShort("role_id"), rs.getString("role")));
			String  linkToPhoto = rs.getString("photo_scope");
			if(!rs.wasNull()) user.setLinkToPhoto(linkToPhoto);
			return user;
		}
	}
	
}

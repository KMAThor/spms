package nc.ukma.thor.spms.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;

@Repository
public class UserRepositoryJdbcImpl implements UserRepository{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private RoleRepository roleRepository;
	
	@Override
	public User getUserById(long id) {
		List<User> users = jdbcTemplate.query("SELECT * FROM \"user\" WHERE id=?",
				new Object[]{ id }, new UserMapper());
		if(users.isEmpty()) {
			return null;
		}
		else {
			User user = users.get(0);
			user.setRole(roleRepository.getRoleByUser(user));
			return user;
		}
	}

	@Override
	public User getUserByEmail(String email) {
		List<User> users = jdbcTemplate.query("SELECT * FROM \"user\" WHERE email=?",
				new Object[]{ email }, new UserMapper());
		if(users.isEmpty()) {
			return null;
		}
		else {
			User user = users.get(0);
			user.setRole(roleRepository.getRoleByUser(user));
			return user;
		}
	}

	@Override
	public List<User> getUserByTeam(Team team) {
		List<User> users = jdbcTemplate.query("SELECT * FROM \"user\" INNER JOIN user_team ON id=user_id WHERE team_id=?",
				new Object[]{ team.getId() }, new UserMapper());
		for(User user: users){
			user.setRole(roleRepository.getRoleByUser(user));
		}
		return users;
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
			return user;
		}
	}
	
}

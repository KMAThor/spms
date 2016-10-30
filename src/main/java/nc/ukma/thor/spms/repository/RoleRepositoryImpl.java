package nc.ukma.thor.spms.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.entity.User;

@Repository
public class RoleRepositoryImpl implements RoleRepository{
	
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Role getRoleById(long id) {
		List<Role> roles = jdbcTemplate.query("SELECT * FROM role WHERE id=?;",
				new Object[] { id },
				new RoleMapper());
		if(roles.isEmpty()) return null;
		else return roles.get(0);
	}
	
	@Override
	public Role getRoleByName(String name) {
		List<Role> roles = jdbcTemplate.query("SELECT * FROM role WHERE role=?;",
				new Object[] { name },
				new RoleMapper());
		if(roles.isEmpty()) return null;
		else return roles.get(0);
	}
	
	@Override
	public Role getRoleByUser(User user) {
		List<Role> roles = jdbcTemplate.query(
				"SELECT * FROM role INNER JOIN user_role ON id=role_id "
				+ "WHERE user_id=?;",
				new Object[] { user.getId() },
				new RoleMapper());
		if(roles.isEmpty()) return null;
		else return roles.get(0);
	}

	
	private static final class RoleMapper implements RowMapper<Role>{
		@Override
		public Role mapRow(ResultSet rs, int rowNum) throws SQLException {
			Role role = new Role();
			role.setId(rs.getLong("id"));
			role.setName(rs.getString("role"));
			return role;
		}
	}

}

package nc.ukma.thor.spms.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.entity.User;

@Repository
public class RoleRepositoryImpl implements RoleRepository{
	
	private static final String GET_ROLE_BY_ID_SQL = "SELECT * FROM role WHERE id=?;";
	private static final String GET_ROLE_BY_NAME_SQL = "SELECT * FROM role WHERE role=?;";
	private static final String GET_ROLE_BY_USER_SQL = "SELECT * FROM role "
			+ "INNER JOIN user_role ON id=role_id "
			+ "WHERE user_id=?;";
	
	private static final RowMapper<Role> ROLE_MAPPER = new RoleMapper();
	@Autowired
	JdbcTemplate jdbcTemplate;

	@Override
	public Role getRoleById(long id) {
		try {
			return jdbcTemplate.queryForObject(GET_ROLE_BY_ID_SQL,
						new Object[] { id }, ROLE_MAPPER);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public Role getRoleByName(String name) {
		try {
			return jdbcTemplate.queryForObject(GET_ROLE_BY_NAME_SQL,
					new Object[] { name }, ROLE_MAPPER);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
	}
	
	@Override
	public Role getRoleByUser(User user) {
		try{
			return jdbcTemplate.queryForObject(GET_ROLE_BY_USER_SQL,
						new Object[] { user.getId() }, ROLE_MAPPER);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
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

package nc.ukma.thor.spms.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryJdbcTemplateImpl implements UserRepository{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public long getNumberOfUsers() {
		int numberOfUsers = this.jdbcTemplate.queryForObject(
		        "select count(*) from \"user\"", Integer.class);
		return numberOfUsers;
	}

}

package nc.ukma.thor.spms.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.User;

@Repository
public class ProjectRepositoryImpl extends AbstractRepository<Project> implements ProjectRepository{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void add(Project p) {
		final String INSERT_SQL =
				"INSERT INTO project (name, description, start_date, end_date, is_completed, chief_mentor_id) "
				+"VALUES (?,?,?,?,?,?);";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(
			    new PreparedStatementCreator() {
			        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] {"id"});
			            ps.setString(1, p.getName());
			            ps.setString(2, p.getDescription());
			            ps.setTimestamp(3, p.getStartDate());
			            ps.setTimestamp(4, p.getEndDate());
			            ps.setBoolean(5, false);
			            ps.setLong(6, p.getChiefUser().getId());
			            return ps;
			        }
			    },
			    keyHolder);
		p.setId((long) keyHolder.getKey());		
	}

	@Override
	public void update(Project p) {
		jdbcTemplate.update(
				"UPDATE project SET name=?, description=?, start_date=?, "
				+ "end_date=?, is_completed=?, chief_mentor_id=? "
				+ "WHERE id=?;",
		new Object[] {
				p.getName(), 
				p.getDescription(), 
				p.getStartDate(),
				p.getEndDate(),
				p.isCompleated(),
				p.getChiefUser().getId(),
				p.getId()
		});
		
	}

	@Override
	public void delete(Project p) {
		jdbcTemplate.update("DELETE FROM project WHERE id=?;",p.getId());
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
			pr.setChiefUser(new User(rs.getLong("chief_mentor_id")));
			return pr;
		}
	}

	@Override
	public Project getById(long id) {
		List<Project> tfList =  jdbcTemplate.query("SELECT * FROM project WHERE id=?;",
				new Object[] { id },
				new ProjectMapper());
		if(tfList.isEmpty()) return null;
		else return tfList.get(0);
	}
}
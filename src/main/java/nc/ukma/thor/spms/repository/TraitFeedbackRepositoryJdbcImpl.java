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

import nc.ukma.thor.spms.entity.Trait;
import nc.ukma.thor.spms.entity.TraitFeedback;

@Repository
public class TraitFeedbackRepositoryJdbcImpl implements TraitFeedbackRepository{

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void add(TraitFeedback f, long appendedToId) {
		final String INSERT_SQL =
				"INSERT INTO trait_feedback (score, comment, trait_id, feedback_id) "
				+"VALUES (?,?,?,?);";
		
		KeyHolder keyHolder = new GeneratedKeyHolder();
		
		jdbcTemplate.update(
			    new PreparedStatementCreator() {
			        public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
			            PreparedStatement ps = connection.prepareStatement(INSERT_SQL, new String[] {"id"});
			            ps.setInt(1, f.getScore());
			            ps.setString(2, f.getComment());
			            ps.setLong(3, f.getTrait().getId());
			            ps.setLong(4, appendedToId);
			            return ps;
			        }
			    },
			    keyHolder);
		f.setId((long) keyHolder.getKey());
	}

	@Override
	public void update(TraitFeedback f) {
		jdbcTemplate.update(
				"UPDATE trait_feedback SET score=?, comment=?, trait_id=? "
				+ "WHERE id=?;",
		new Object[] {
				f.getScore(), 
				f.getComment(), 
				f.getTrait().getId(), 
				f.getId()
		});
	}

	@Override
	public void delete(TraitFeedback f) {
		jdbcTemplate.update("DELETE FROM trait_feedback WHERE id=?;",f.getId());
	}

	@Override
	public TraitFeedback getById(long id) {
		List<TraitFeedback> tfs =  jdbcTemplate.query("SELECT * FROM trait_feedback WHERE id=?;",
				new Object[] { id },
				new TraitFeedbackMapper());
		if(tfs.isEmpty()) return null;
		else return tfs.get(0);
	}

	@Override
	public List<TraitFeedback> getAllAppendedTo(long appendedToId) {
		List<TraitFeedback> tfs = jdbcTemplate.query("SELECT * FROM trait_feedback WHERE feedback_id=?;",
				new Object[] { appendedToId },
				new TraitFeedbackMapper());
		return tfs;
	}
	
	private static final class TraitFeedbackMapper implements RowMapper<TraitFeedback> {
		@Override
		public TraitFeedback mapRow(ResultSet rs, int rowNum) throws SQLException {
			TraitFeedback tf = new TraitFeedback();
			tf.setId(rs.getLong("id"));
			tf.setScore(rs.getInt("score"));
			tf.setComment(rs.getString("comment"));
			tf.setTrait(new Trait(rs.getLong("trait_id")));
			return tf;
		}
	}
	
}

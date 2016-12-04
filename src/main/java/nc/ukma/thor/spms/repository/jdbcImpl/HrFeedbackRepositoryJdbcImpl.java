package nc.ukma.thor.spms.repository.jdbcImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import nc.ukma.thor.spms.entity.HrFeedback;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.repository.HrFeedbackRepository;

@Repository
public class HrFeedbackRepositoryJdbcImpl implements HrFeedbackRepository {

	private static final String GET_HR_FEEDBACK_BY_ID_SQL = "select * from hr_feedback where id = ?";
	private static final String GET_HR_FEEDBACK_BY_STUDENT_ID_SQL = "select * from hr_feedback where student_id = ?";
	private static final String GET_HR_FEEDBACK_BY_ADDED_BY_ID_SQL = "select * from hr_feedback where added_by_id = ?";
	private static final String GET_HR_FEEDBACK_BY_AUTHOR_ID_SQL = "select * from hr_feedback where author_id = ?";

	private static final HrFeedbackMapper HR_FEEDBACK_MAPPER = new HrFeedbackMapper();
	private static final String UPDATE_HR_FEEDBACK_SQL = "update hr_feedback set "
			+ "topic = ?, summary = ?, student_id = ?, added_by_id = ?, author_id = ? where id = ?";
	private static final String DELETE_HR_FEEDBACK_SQL = "DELETE FROM hr_feedback WHERE id=?;";
	private static final String INSER_HR_FEEDBACK_SQL = "insert into hr_feedback"
			+ "(topic, summary, author_id, student_id, added_by_id) values (?, ?, ?, ?, ?)";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void add(HrFeedback hrFeedback) {
		// TODO Auto-generated method stub
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(INSER_HR_FEEDBACK_SQL, new String[] { "id" });
			ps.setString(1, hrFeedback.getTopic());
			ps.setString(2, hrFeedback.getSummary());
			ps.setLong(3, hrFeedback.getAuthor().getId());
			ps.setLong(4, hrFeedback.getStudent().getId());
			ps.setLong(5, hrFeedback.getAdded_by().getId());
			return ps;
		}, keyHolder);
		hrFeedback.setId((Long) keyHolder.getKey());
	}

	@Override
	public void update(HrFeedback hrFeedback) {
		// TODO Auto-generated method stub
		jdbcTemplate.update(UPDATE_HR_FEEDBACK_SQL, new Object[] {
				hrFeedback.getTopic(),
				hrFeedback.getSummary(),
				hrFeedback.getStudent().getId(),
				hrFeedback.getAdded_by().getId(),
				hrFeedback.getAuthor().getId(),
				hrFeedback.getId()
		});
	}

	@Override
	public void delete(HrFeedback hrFeedback) {
		// TODO Auto-generated method stub
		jdbcTemplate.update(DELETE_HR_FEEDBACK_SQL, hrFeedback.getId());
	}

	@Override
	public HrFeedback getById(Long hrFeedbackId) {
		List<HrFeedback> hrFeedbacks = jdbcTemplate.query(GET_HR_FEEDBACK_BY_ID_SQL, new Object[] { hrFeedbackId },
				HR_FEEDBACK_MAPPER);
		if (hrFeedbacks.isEmpty())
			return null;
		else
			return hrFeedbacks.get(0);
	}

	@Override
	public List<HrFeedback> getHrFeedbacksByStudent(User student) {
		return jdbcTemplate.query(GET_HR_FEEDBACK_BY_STUDENT_ID_SQL, new Object[] { student.getId() },
				HR_FEEDBACK_MAPPER);
	}

	@Override
	public List<HrFeedback> getHrFeedbacksByHr(User addedBy) {
		return jdbcTemplate.query(GET_HR_FEEDBACK_BY_ADDED_BY_ID_SQL, new Object[] { addedBy.getId() },
				HR_FEEDBACK_MAPPER);
	}

	@Override
	public List<HrFeedback> getHrFeedbacksByAuthor(User author) {
		return jdbcTemplate.query(GET_HR_FEEDBACK_BY_AUTHOR_ID_SQL, new Object[] { author.getId() },
				HR_FEEDBACK_MAPPER);
	}

	private static final class HrFeedbackMapper implements RowMapper<HrFeedback> {
		@Override
		public HrFeedback mapRow(ResultSet rs, int rowNum) throws SQLException {
			HrFeedback hrFeedback = new HrFeedback();
			hrFeedback.setId(rs.getLong("id"));
			hrFeedback.setTopic(rs.getString("topic"));
			hrFeedback.setSummary(rs.getString("summary"));
			User student = new User();
			student.setId(rs.getLong("student_id"));
			hrFeedback.setStudent(student);
			User addedBy = new User();
			addedBy.setId(rs.getLong("added_by_id"));
			hrFeedback.setAdded_by(addedBy);
			User author = new User();
			author.setId(rs.getLong("author_id"));
			hrFeedback.setAuthor(author);
			return hrFeedback;
		}
	}

}

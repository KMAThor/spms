package nc.ukma.thor.spms.repository.jdbcImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.MeetingFeedback;
import nc.ukma.thor.spms.entity.TraitFeedback;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.repository.MeetingFeedbackRepository;
import nc.ukma.thor.spms.repository.TraitFeedbackRepository;

@Repository
public class MeetingFeedbackRepositoryJdbcImpl implements MeetingFeedbackRepository{
	
	private static final String INSER_MEETING_FEEDBACK_SQL = "INSERT INTO meeting_feedback "
			+ "(summary, student_id, meeting_id, author_id) VALUES (?,?,?,?)";
	private static final String UPDATE_MEETING_FEEDBACK_SQL = "UPDATE meeting_feedback "
			+ "SET summary=?, student_id=?, meeting_id=? WHERE id=?;";
	private static final String DELETE_MEETING_FEEDBACK_SQL = "DELETE FROM meeting_feedback WHERE id=?;";
	private static final String GET_MEETING_FEEDBACK_BY_ID_SQL = "SELECT "
			+ "meeting_feedback.id AS meeting_feedback_id, summary, student_id, meeting_id, author_id,"
			+ "trait_feedback.id AS trait_feedback_id, score, comment, trait_id, first_name, second_name, "
			+ "last_name, topic, start_date "
			+ "FROM meeting_feedback "
			+ "INNER JOIN \"user\" ON meeting_feedback.student_id=\"user\".id "
			+ "INNER JOIN meeting ON meeting_feedback.meeting_id=meeting.id "
			+ "LEFT JOIN trait_feedback ON meeting_feedback.id=trait_feedback.meeting_feedback_id "
			+ "WHERE meeting_feedback.id=? "
			+ "ORDER BY meeting_feedback_id, trait_feedback_id;";
	
	private static final String GET_MEETING_FEEDBACKS_BY_MEETING_SQL = "";
	private static final String GET_MEETING_FEEDBACK_BY_STUDENT_SQL = "";
	private static final String GET_MEETING_FEEDBACK_BY_MENTOR_SQL = "";
	private static final String GET_MEETING_FEEDBACK_WITHOUT_TRAITS_BY_MEETING_AND_STUDENT_SQL = "SELECT * "
			+ "FROM meeting_feedback "
			+ "WHERE meeting_id=? AND student_id=?;";
	
	private static final String GET_MEETING_FEEDBACK_BY_MEETING_STUDENT_AUTHOR_SQL = "SELECT "
			+ "meeting_feedback.id AS meeting_feedback_id, summary, student_id, meeting_id, author_id,"
			+ "trait_feedback.id AS trait_feedback_id, score, comment, trait_id, first_name, second_name, "
			+ "last_name, topic, start_date "
			+ "FROM meeting_feedback "
			+ "INNER JOIN \"user\" ON meeting_feedback.student_id=\"user\".id "
			+ "INNER JOIN meeting ON meeting_feedback.meeting_id=meeting.id "
			+ "LEFT JOIN trait_feedback ON meeting_feedback.id=trait_feedback.meeting_feedback_id "
			+ "WHERE meeting_id=? AND student_id=? AND author_id=? "
			+ "ORDER BY meeting_feedback_id, trait_feedback_id;";
	
	private static final String GET_MEETING_FEEDBACK_WITHOUT_TRAITS_BY_MEETING_STUDENT_AUTHOR_SQL = "SELECT * "
			+ "FROM meeting_feedback "
			+ "WHERE meeting_id=? AND student_id=? AND author_id=?;";
	
	private static final MeetingFeedbackExtractor MEETING_FEEDBACKS_EXTRACTOR = new MeetingFeedbackExtractor();
	private static final RowMapper<MeetingFeedback> MEETING_FEEDBACK_WITHOUT_TRAITS_MAPPER = new MeetingFeedbackWithoutTraitsMapper();
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public void add(MeetingFeedback meeetingFeedback) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(INSER_MEETING_FEEDBACK_SQL,
					new String[] {"id"}); 
			ps.setString(1, meeetingFeedback.getSummary());
			ps.setLong(2, meeetingFeedback.getStudent().getId());
			ps.setLong(3, meeetingFeedback.getMeeting().getId());
			ps.setLong(4, meeetingFeedback.getAuthor().getId());
			return ps;
			},keyHolder);
		meeetingFeedback.setId((Long) keyHolder.getKey());
	}

	@Override
	public void update(MeetingFeedback meeetingFeedback) {
		jdbcTemplate.update(UPDATE_MEETING_FEEDBACK_SQL,
				new Object[]{ meeetingFeedback.getSummary(),
						meeetingFeedback.getStudent().getId(),
						meeetingFeedback.getMeeting().getId(),
						//meeetingFeedback.getAuthor().getId(),
						meeetingFeedback.getId()
				});
	}

	@Override
	public void delete(MeetingFeedback meeetingFeedback) {
		//All trait feedbacks connected with this meeting feedback will be automatically deleted 
		jdbcTemplate.update(DELETE_MEETING_FEEDBACK_SQL, meeetingFeedback.getId());
	}

	@Override
	public MeetingFeedback getById(Long meeetingFeedbackId) {
		List<MeetingFeedback> meetingFeedbacks =jdbcTemplate.query(GET_MEETING_FEEDBACK_BY_ID_SQL, new Object[] {meeetingFeedbackId }, MEETING_FEEDBACKS_EXTRACTOR);
		if(meetingFeedbacks.isEmpty()) return null;
		else return meetingFeedbacks.get(0);
	}

	@Override
	public List<MeetingFeedback> getMeetingFeedbacksByMeeting(Meeting meeting) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MeetingFeedback> getMeetingFeedbacksByStudent(User student) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<MeetingFeedback> getMeetingFeedbacksByMentor(User mentor) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public List<MeetingFeedback> getMeetingFeedbacksWithoutTraitsByMeetingAndStudent(Meeting meeting, User student) {
		return jdbcTemplate.query(GET_MEETING_FEEDBACK_WITHOUT_TRAITS_BY_MEETING_AND_STUDENT_SQL, 
				new Object[] {meeting.getId(), student.getId()}, MEETING_FEEDBACK_WITHOUT_TRAITS_MAPPER);
	}

	private static final class MeetingFeedbackWithoutTraitsMapper implements RowMapper<MeetingFeedback> {
		@Override
		public MeetingFeedback mapRow(ResultSet rs, int rowNum) throws SQLException {
			MeetingFeedback meetingFeedback = new MeetingFeedback(rs.getLong("id"),
					rs.getString("summary"),
					new User(rs.getLong("student_id")),
					new Meeting(rs.getLong("meeting_id")),
					new User(rs.getLong("author_id")));
			return meetingFeedback;
		}
	}
	
	private static final class MeetingFeedbackExtractor implements ResultSetExtractor<List<MeetingFeedback>>{
		@Override
		public List<MeetingFeedback> extractData(ResultSet rs) throws SQLException, DataAccessException {
			List<MeetingFeedback> meetingFeedbacks = new ArrayList<MeetingFeedback>();
			MeetingFeedback meetingFeedback = null;
			while(rs.next()){
				if(meetingFeedback != null && !meetingFeedback.getId().equals(rs.getLong("meeting_feedback_id"))) {
					meetingFeedbacks.add(meetingFeedback);
					meetingFeedback = null;
				}
				if(meetingFeedback == null) {
					User student = new User();
					student.setId(rs.getLong("student_id"));
					student.setFirstName(rs.getString("first_name"));
					student.setSecondName(rs.getString("second_name"));
					student.setLastName(rs.getString("last_name"));
					Meeting meeting = new Meeting(rs.getLong("meeting_id"), rs.getString("topic"), rs.getTimestamp("start_date"));
					meetingFeedback = new MeetingFeedback(rs.getLong("meeting_feedback_id"),
							rs.getString("summary"), student,
							meeting, new User(rs.getLong("author_id")));
				}
				meetingFeedback.addTraitFeedback(new TraitFeedback(rs.getLong("trait_feedback_id"),
						rs.getShort("score"), rs.getString("comment"),rs.getLong("trait_id")));
			}
			meetingFeedbacks.add(meetingFeedback);
			return meetingFeedbacks;
		}
	}

	@Override
	public MeetingFeedback getMeetingFeedbacksByMeetingStudentAuthor(Meeting meeting, User student, User author) {
		List<MeetingFeedback> meetingFeedbacks =jdbcTemplate.query(GET_MEETING_FEEDBACK_BY_MEETING_STUDENT_AUTHOR_SQL,
				new Object[] {meeting.getId(), student.getId(), author.getId() }, MEETING_FEEDBACKS_EXTRACTOR);
		if(meetingFeedbacks.isEmpty()) return null;
		else return meetingFeedbacks.get(0);
	}
	@Override
	public MeetingFeedback getMeetingFeedbacksWithoutTraitsByMeetingStudentAuthor(Meeting meeting, User student, User author) {
		List<MeetingFeedback> meetingFeedbacks =jdbcTemplate.query(GET_MEETING_FEEDBACK_WITHOUT_TRAITS_BY_MEETING_STUDENT_AUTHOR_SQL,
				new Object[] {meeting.getId(), student.getId(), author.getId() }, MEETING_FEEDBACK_WITHOUT_TRAITS_MAPPER);
		if(meetingFeedbacks.isEmpty()) return null;
		else return meetingFeedbacks.get(0);
	}
	
}

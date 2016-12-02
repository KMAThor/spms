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
			+ "SET summary=?, student_id=?, meeting_id=?, author_id=? WHERE id=?;";
	private static final String DELETE_MEETING_FEEDBACK_SQL = "DELETE FROM meeting_feedback WHERE id=?;";
	private static final String GET_MEETING_FEEDBACK_BY_ID_SQL = "SELECT "
			+ "meeting_feedback.id AS meeting_feedback_id, summary, student_id, meeting_id, author_id,"
			+ "trait_feedback.id AS trait_feedback_id, score, comment, trait_id "
			+ "FROM meeting_feedback "
			+ "INNER JOIN trait_feedback ON meeting_feedback.id=trait_feedback.meeting_feedback_id "
			+ "WHERE meeting_feedback.id=? "
			+ "ORDER BY meeting_feedback_id, trait_feedback_id;";
	
	private static final String GET_MEETING_FEEDBACKS_BY_MEETING_SQL = "";
	private static final String GET_MEETING_FEEDBACK_BY_STUDENT_SQL = "";
	private static final String GET_MEETING_FEEDBACK_BY_MENTOR_SQL = "";
	private static final String GET_MEETING_FEEDBACK_BY_MEETING_AND_STUDENT_SQL = "";
	
	private static final String GET_MEETING_FEEDBACK_BY_MEETING_STUDENT_MENTOR_SQL = "SELECT "
			+ "meeting_feedback.id AS meeting_feedback_id, summary, student_id, meeting_id, author_id,"
			+ "trait_feedback.id AS trait_feedback_id, score, comment, trait_id "
			+ "FROM meeting_feedback "
			+ "INNER JOIN trait_feedback ON meeting_feedback.id=trait_feedback.meeting_feedback_id "
			+ "WHERE meeting_id=? AND student_id=? AND author_id=? "
			+ "ORDER BY meeting_feedback_id, trait_feedback_id;";
	
	private static final MeetingFeedbackExtractor MEETING_FEEDBACKS_EXTRACTOR = new MeetingFeedbackExtractor();
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private TraitFeedbackRepository traitFeedbackRepository;
	
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
		traitFeedbackRepository.addTraitFeedbacks(meeetingFeedback.getTraitFeedbacks(), meeetingFeedback);
	}

	@Override
	public void update(MeetingFeedback meeetingFeedback) {
		jdbcTemplate.update(UPDATE_MEETING_FEEDBACK_SQL,
				new Object[]{ meeetingFeedback.getSummary(),
						meeetingFeedback.getStudent().getId(),
						meeetingFeedback.getMeeting().getId(),
						meeetingFeedback.getAuthor().getId(),
						meeetingFeedback.getId()
				});
		traitFeedbackRepository.updateTraitFeedbacks(meeetingFeedback.getTraitFeedbacks());
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
	public List<MeetingFeedback> getMeetingFeedbacksByMeetingAndStudent(Meeting meeting, User user) {
		// TODO Auto-generated method stub
		return null;
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
					meetingFeedback = new MeetingFeedback(rs.getLong("meeting_feedback_id"),
							rs.getString("summary"), rs.getLong("student_id"),
							rs.getLong("meeting_id"),rs.getLong("author_id"));
				}
				meetingFeedback.addTraitFeedback(new TraitFeedback(rs.getLong("trait_feedback_id"),
						rs.getShort("score"), rs.getString("comment"),rs.getLong("trait_id")));
			}
			meetingFeedbacks.add(meetingFeedback);
			return meetingFeedbacks;
		}
	}

	@Override
	public MeetingFeedback getMeetingFeedbacksByMeetingStudentMentor(Meeting meeting, User student, User mentor) {
		List<MeetingFeedback> meetingFeedbacks =jdbcTemplate.query(GET_MEETING_FEEDBACK_BY_MEETING_STUDENT_MENTOR_SQL,
				new Object[] {meeting.getId(), student.getId(), mentor.getId() }, MEETING_FEEDBACKS_EXTRACTOR);
		if(meetingFeedbacks.isEmpty()) return null;
		else return meetingFeedbacks.get(0);
	}
	
}

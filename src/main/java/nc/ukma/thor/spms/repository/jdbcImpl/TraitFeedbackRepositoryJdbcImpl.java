package nc.ukma.thor.spms.repository.jdbcImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import nc.ukma.thor.spms.entity.MeetingFeedback;
import nc.ukma.thor.spms.entity.TraitFeedback;
import nc.ukma.thor.spms.repository.TraitFeedbackRepository;

@Repository
public class TraitFeedbackRepositoryJdbcImpl implements TraitFeedbackRepository{
	
	private static final String INSERT_TRAIT_FEEDBACK_SQL = "INSERT INTO trait_feedback "
			+ "(score, comment, trait_id, meeting_feedback_id, hr_feedback_id) "
			+ "VALUES (?,?,?,?,?);";
	private static final String UPDATE_TRAIT_FEEDBACK_SQL = "UPDATE trait_feedback "
			+ "SET score=?, comment=? "
			+ "WHERE id=?;";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void addTraitFeedbacks(List<TraitFeedback> traitFeedbacks, MeetingFeedback meetingFeedback) {
		List<Object[]> batch = new ArrayList<Object[]>();
		for(TraitFeedback traitFeedback: traitFeedbacks){
			batch.add(new Object[] {
					traitFeedback.getScore(),
					traitFeedback.getComment(),
					traitFeedback.getTrait().getId(),
					meetingFeedback.getId(),
					null});
		}
		jdbcTemplate.batchUpdate(INSERT_TRAIT_FEEDBACK_SQL, batch);
	}
	@Override
	public void updateTraitFeedbacks(List<TraitFeedback> traitFeedbacks) {
		List<Object[]> batch = new ArrayList<Object[]>();
		for(TraitFeedback traitFeedback: traitFeedbacks){
			batch.add(new Object[] {
					traitFeedback.getScore(),
					traitFeedback.getComment(),
					traitFeedback.getId()});
		}
		jdbcTemplate.batchUpdate(UPDATE_TRAIT_FEEDBACK_SQL, batch);
	}
	
	
	
}

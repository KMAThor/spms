package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.MeetingFeedback;
import nc.ukma.thor.spms.entity.TraitFeedback;

public interface TraitFeedbackRepository {
	
	public void addTraitFeedbacks(List<TraitFeedback> traitFeedbacks, MeetingFeedback meetingFeedback);
	public void updateTraitFeedbacks(List<TraitFeedback> traitFeedbacks);
	
}

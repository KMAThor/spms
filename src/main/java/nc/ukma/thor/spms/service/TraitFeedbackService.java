package nc.ukma.thor.spms.service;

import java.util.List;

import nc.ukma.thor.spms.entity.MeetingFeedback;
import nc.ukma.thor.spms.entity.TraitFeedback;

public interface TraitFeedbackService {
	
	public void addTraitFeedbacks(List<TraitFeedback> traitFeedbacks, MeetingFeedback meetingFeedback);
	public void updateTraitFeedbacks(List<TraitFeedback> traitFeedbacks);
	public List<TraitFeedback> getTraitFeedbacksByMeetingFeedback(MeetingFeedback meetingFeedback);

}

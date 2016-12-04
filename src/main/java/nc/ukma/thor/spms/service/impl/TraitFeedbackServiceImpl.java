package nc.ukma.thor.spms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nc.ukma.thor.spms.entity.MeetingFeedback;
import nc.ukma.thor.spms.entity.TraitFeedback;
import nc.ukma.thor.spms.repository.TraitFeedbackRepository;
import nc.ukma.thor.spms.service.TraitFeedbackService;

@Service
public class TraitFeedbackServiceImpl implements TraitFeedbackService{

	@Autowired
	private TraitFeedbackRepository traitFeedbackRepository;
	
	@Override
	public void addTraitFeedbacks(List<TraitFeedback> traitFeedbacks, MeetingFeedback meetingFeedback) {
		traitFeedbackRepository.addTraitFeedbacks(traitFeedbacks, meetingFeedback);
	}

	@Override
	public void updateTraitFeedbacks(List<TraitFeedback> traitFeedbacks) {
		traitFeedbackRepository.updateTraitFeedbacks(traitFeedbacks);
	}
	
	@Override
	public void deleteTraitFeedbacks(List<TraitFeedback> traitFeedbacks) {
		traitFeedbackRepository.deleteTraitFeedbacks(traitFeedbacks);
	}

	@Override
	public List<TraitFeedback> getTraitFeedbacksByMeetingFeedback(MeetingFeedback meetingFeedback) {
		return traitFeedbackRepository.getTraitFeedbacksByMeetingFeedback(meetingFeedback);
	}

}

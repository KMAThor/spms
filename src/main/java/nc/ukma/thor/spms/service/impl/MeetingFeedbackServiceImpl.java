package nc.ukma.thor.spms.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import nc.ukma.thor.spms.entity.MeetingFeedback;
import nc.ukma.thor.spms.entity.TraitFeedback;
import nc.ukma.thor.spms.repository.MeetingFeedbackRepository;
import nc.ukma.thor.spms.service.MeetingFeedbackService;
import nc.ukma.thor.spms.service.TraitFeedbackService;

@Service
public class MeetingFeedbackServiceImpl extends AbstractService<MeetingFeedback> implements MeetingFeedbackService{
	
	@Autowired
	private TraitFeedbackService traitFeedbackService;
	private MeetingFeedbackRepository meetingFeedbackRepository;
	
	@Autowired
	public MeetingFeedbackServiceImpl(MeetingFeedbackRepository meetingFeedbackRepository) {
		super(meetingFeedbackRepository);
		this.meetingFeedbackRepository = meetingFeedbackRepository;
	}
	/*
	 * This method saves meetingFeedback
	 * Empty trait feedbacks won't be saved
	 * */
	@Override
	@Transactional
	public void create(MeetingFeedback meetingFeedback){
		List<TraitFeedback> traitFeedbacks = meetingFeedback.getTraitFeedbacks();
		traitFeedbacks.removeIf(traitFeedback -> {
			return traitFeedback.isEmpty();
		});
		meetingFeedbackRepository.add(meetingFeedback);
		traitFeedbackService.addTraitFeedbacks(traitFeedbacks, meetingFeedback);
	}
	/*
	 * This method updates MeetingFeedback.
	 * Not empty trait feedbacks will be updated or added
	 * Empty will be ignored or deleted if present before
	 * */
	
	@Override
	@Transactional
	public void update(MeetingFeedback meetingFeedback){
		List<TraitFeedback> oldTraitFeedbacks = traitFeedbackService.getTraitFeedbacksByMeetingFeedback(meetingFeedback);
		List<TraitFeedback> traitFeedbacks = meetingFeedback.getTraitFeedbacks();
		List<TraitFeedback> traitFeedbacksToCreate = new ArrayList<TraitFeedback>();
		List<TraitFeedback> traitFeedbacksToUpdate = new ArrayList<TraitFeedback>();
		List<TraitFeedback> traitFeedbacksToDelete = new ArrayList<TraitFeedback>();
		
		for(int i = 0; i < traitFeedbacks.size(); i++){
			TraitFeedback traitFeedback = traitFeedbacks.get(i);
			if(traitFeedback.isEmpty()){
				if(oldTraitFeedbacks.contains(traitFeedback)) traitFeedbacksToDelete.add(traitFeedback);
			} else {
				if(oldTraitFeedbacks.contains(traitFeedback)) traitFeedbacksToUpdate.add(traitFeedback);
				else traitFeedbacksToCreate.add(traitFeedback);
			}
		}
		
		meetingFeedbackRepository.update(meetingFeedback);
		traitFeedbackService.addTraitFeedbacks(traitFeedbacksToCreate, meetingFeedback);
		traitFeedbackService.updateTraitFeedbacks(traitFeedbacksToUpdate);
		traitFeedbackService.updateTraitFeedbacks(traitFeedbacksToDelete);
	}
	
}

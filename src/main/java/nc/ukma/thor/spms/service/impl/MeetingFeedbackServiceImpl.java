package nc.ukma.thor.spms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nc.ukma.thor.spms.entity.MeetingFeedback;
import nc.ukma.thor.spms.repository.MeetingFeedbackRepository;
import nc.ukma.thor.spms.service.MeetingFeedbackService;

@Service
public class MeetingFeedbackServiceImpl extends AbstractService<MeetingFeedback> implements MeetingFeedbackService{

	private MeetingFeedbackRepository meetingFeedbackRepository;
	
	@Autowired
	public MeetingFeedbackServiceImpl(MeetingFeedbackRepository meetingFeedbackRepository) {
		super(meetingFeedbackRepository);
		this.meetingFeedbackRepository = meetingFeedbackRepository;
	}
	
}

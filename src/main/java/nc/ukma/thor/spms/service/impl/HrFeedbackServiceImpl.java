package nc.ukma.thor.spms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nc.ukma.thor.spms.entity.HrFeedback;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.repository.HrFeedbackRepository;
import nc.ukma.thor.spms.repository.MeetingFeedbackRepository;
import nc.ukma.thor.spms.service.HrFeedbackService;

@Service
public class HrFeedbackServiceImpl extends AbstractService<HrFeedback> implements HrFeedbackService{

	@Autowired
	private HrFeedbackRepository hrFeedbackRepository;
	
	@Autowired
	public HrFeedbackServiceImpl(HrFeedbackRepository hrFeedbackRepository) {
		super(hrFeedbackRepository);
		this.hrFeedbackRepository = hrFeedbackRepository;
	}
	
	@Override
	public List<HrFeedback> getHrFeedbacksByStudent(User id) {
		return hrFeedbackRepository.getHrFeedbacksByStudent(id);
	}

	@Override
	public List<HrFeedback> getHrFeedbacksByHr(User id) {
		return hrFeedbackRepository.getHrFeedbacksByHr(id);
	}

	@Override
	public List<HrFeedback> getHrFeedbacksByAuthor(User id) {
		return hrFeedbackRepository.getHrFeedbacksByAuthor(id);
	}
	
	

}

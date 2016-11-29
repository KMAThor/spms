package nc.ukma.thor.spms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nc.ukma.thor.spms.entity.HrFeedback;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.repository.HrFeedbackRepository;

@Service
public class HrFeedbackServiceImpl implements HrFeedbackService {

	@Autowired
	private HrFeedbackRepository hrFeedRepository;
	
	@Override
	public void create(HrFeedback entity) {
		// TODO Auto-generated method stub
	}

	@Override
	public void update(HrFeedback entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(HrFeedback entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public HrFeedback getById(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HrFeedback> getHrFeedbacksByStudent(User id) {
		return hrFeedRepository.getHrFeedbacksByStudent(id);
	}

	@Override
	public List<HrFeedback> getHrFeedbacksByHr(User id) {
		return hrFeedRepository.getHrFeedbacksByHr(id);
	}

	@Override
	public List<HrFeedback> getHrFeedbacksByAuthor(User id) {
		return hrFeedRepository.getHrFeedbacksByAuthor(id);
	}
	
	

}

package nc.ukma.thor.spms.service;

import java.util.List;

import nc.ukma.thor.spms.entity.HrFeedback;
import nc.ukma.thor.spms.entity.User;

public interface HrFeedbackService extends Service<HrFeedback>{

	public List<HrFeedback> getHrFeedbacksByStudent(User id);
	public List<HrFeedback> getHrFeedbacksByHr(User id);
	public List<HrFeedback> getHrFeedbacksByAuthor(User id);
	
}

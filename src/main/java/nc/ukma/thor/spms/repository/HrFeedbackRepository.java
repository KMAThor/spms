package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.HrFeedback;
import nc.ukma.thor.spms.entity.User;

public interface HrFeedbackRepository extends MyRepository<HrFeedback>{
	
	public List<HrFeedback> getHrFeedbacksByStudent(User id);
	public List<HrFeedback> getHrFeedbacksByHr(User id);
	public List<HrFeedback> getHrFeedbacksByAuthor(User id);
	
}

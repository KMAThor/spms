package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.HrFeedback;
import nc.ukma.thor.spms.entity.User;

public interface HrFeedbackRepository {
	
	public void add(HrFeedback mf);
	public void update(HrFeedback mf);
	public void delete(HrFeedback mf);

	public HrFeedback getById(Long id);
	public List<HrFeedback> getHrFeedbacksByStudent(User id);
	public List<HrFeedback> getHrFeedbacksByHr(User id);
	public List<HrFeedback> getHrFeedbacksByAuthor(User id);
}

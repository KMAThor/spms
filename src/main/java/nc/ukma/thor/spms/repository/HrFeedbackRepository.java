package nc.ukma.thor.spms.repository;

import nc.ukma.thor.spms.entity.HrFeedback;
import nc.ukma.thor.spms.entity.User;

public interface HrFeedbackRepository {
	
	public void add(HrFeedback mf);
	public void update(HrFeedback mf);
	public void delete(HrFeedback mf);

	public HrFeedback getById(Long id);
	public HrFeedback getByStudent(User id);
	public HrFeedback getByHr(User id);
	public HrFeedback getByAuthor(User id);
}

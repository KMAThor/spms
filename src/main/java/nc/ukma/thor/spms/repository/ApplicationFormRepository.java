package nc.ukma.thor.spms.repository;

import nc.ukma.thor.spms.entity.ApplicationForm;
import nc.ukma.thor.spms.entity.User;

public interface ApplicationFormRepository {
	
	public void getApplicationFormById(ApplicationForm af);
	public void getApplicationFormByUser(User user);

}

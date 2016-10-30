package nc.ukma.thor.spms.repository;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.MeetingFeedback;
import nc.ukma.thor.spms.entity.User;

public interface MeetingFeedbackRepository {
	
	public void add(MeetingFeedback mf);
	public void update(MeetingFeedback mf);
	public void delete(MeetingFeedback mf);

	public MeetingFeedback getById(Long id);
	public MeetingFeedback getByMeeting(Meeting id);
	public MeetingFeedback getByStudent(User id);
	public MeetingFeedback getByMentor(User id);
}



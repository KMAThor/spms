package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.MeetingFeedback;
import nc.ukma.thor.spms.entity.User;

public interface MeetingFeedbackRepository {
	
	public void add(MeetingFeedback mf);
	public void update(MeetingFeedback mf);
	public void delete(MeetingFeedback mf);

	public MeetingFeedback getById(Long id);
	public List<MeetingFeedback> getMeetingFeedbacksByMeeting(Meeting id);
	public List<MeetingFeedback> getMeetingFeedbacksByStudent(User id);
	public List<MeetingFeedback> getMeetingFeedbacksByMentor(User id);
}



package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.MeetingFeedback;
import nc.ukma.thor.spms.entity.User;

public interface MeetingFeedbackRepository extends MyRepository<MeetingFeedback>{

	public List<MeetingFeedback> getMeetingFeedbacksByMeeting(Meeting meeting);
	public List<MeetingFeedback> getMeetingFeedbacksByStudent(User student);
	public List<MeetingFeedback> getMeetingFeedbacksByMentor(User mentor);
	public List<MeetingFeedback> getMeetingFeedbacksWithoutTraitsByMeetingAndStudent(Meeting meeting, User user);
	public MeetingFeedback getMeetingFeedbacksByMeetingStudentAuthor(Meeting meeting, User student, User mentor);
	public MeetingFeedback getMeetingFeedbacksWithoutTraitsByMeetingStudentAuthor(Meeting meeting, User student, User author);
	
}
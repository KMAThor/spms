package nc.ukma.thor.spms.service;

public interface SpmsWebSecurityService {
	
	public boolean isUserChiefMentorOfProject(String email, long projectId);
	public boolean isUserMentorFromProject(String email, long projectId);
	
	public boolean isUserChiefMentorOfProjectWithTeam(String email, long teamId);
	public boolean isUserChiefMentorOfProjectWithMeeting(String email, long meetingId);
	public boolean isUserChiefMentorOfProjectWithMeetingFeedback(String email, long meetingFeedbackId);
	
	public boolean isUserMentorOfTeam(String email, long teamId);
	public boolean isUserMentorOfTeamWithMeeting(String email, long meetingId);
	public boolean isUserMentorOfTeamWithfMeetingFeedback(String email, long meetingFeedbackId);
	
}

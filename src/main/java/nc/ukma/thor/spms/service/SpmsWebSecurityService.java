package nc.ukma.thor.spms.service;

import nc.ukma.thor.spms.entity.SpmsUserDetails;

public interface SpmsWebSecurityService {
	
	public boolean isUserMemberOfProject(SpmsUserDetails principal, long projectId);
	public boolean isUserMemberOfTeam(SpmsUserDetails principal, long teamId);
	public boolean isUserMemberOfTeamWithMeeting(SpmsUserDetails principal, long meetingId);
	public boolean isUserMemberOfTeamWithfMeetingFeedback(SpmsUserDetails principal, long meetingFeedbackId);
	public boolean isUserMemberOfTeamWithMember(SpmsUserDetails principal, long userId);
	
	public boolean isUserChiefMentorOfProject(SpmsUserDetails principal, long projectId);
	public boolean isUserChiefMentorOfProjectWithTeam(SpmsUserDetails principal, long teamId);
	public boolean isUserChiefMentorOfProjectWithMeeting(SpmsUserDetails principal, long meetingId);
	public boolean isUserChiefMentorOfProjectWithMeetingFeedback(SpmsUserDetails principal, long meetingFeedbackId);
	public boolean isUserChiefMentorOfProjectWithMember(SpmsUserDetails principal, long userId);
	

	
	
}

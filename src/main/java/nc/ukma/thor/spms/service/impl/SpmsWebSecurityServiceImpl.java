package nc.ukma.thor.spms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nc.ukma.thor.spms.entity.SpmsUserDetails;
import nc.ukma.thor.spms.repository.UserRepository;
import nc.ukma.thor.spms.service.SpmsWebSecurityService;

@Service("spmsWebSecurityService")
public class SpmsWebSecurityServiceImpl implements SpmsWebSecurityService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public boolean isUserMemberOfProject(SpmsUserDetails principal, long projectId) {
		return userRepository.isUserMemberOfProject(principal.getId(), projectId);
	}
	@Override
	public boolean isUserMemberOfTeam(SpmsUserDetails principal, long teamId) {
		return userRepository.isUserMemberOfTeam(principal.getId(), teamId);
	}
	@Override
	public boolean isUserMemberOfTeamWithMeeting(SpmsUserDetails principal, long meetingId) {
		return userRepository.isUserMemberOfTeamWithMeeting(principal.getId(), meetingId);
	}
	@Override
	public boolean isUserMemberOfTeamWithMeetingFeedback(SpmsUserDetails principal, long meetingFeedbackId) {
		return userRepository.isUserMemberOfTeamWithMeetingFeedback(principal.getId(), meetingFeedbackId);
	}
	@Override
	public boolean isUserMemberOfTeamWithMember(SpmsUserDetails principal, long userId) {
		return userRepository.isUserMemberOfTeamWithMember(principal.getId(), userId);
	}
	
	@Override      
	public boolean isUserChiefMentorOfProject(SpmsUserDetails principal, long projectId) {
		return userRepository.isUserChiefMentorOfProject(principal.getId(), projectId);
	}
	@Override
	public boolean isUserChiefMentorOfProjectWithTeam(SpmsUserDetails principal, long teamId) {
		return userRepository.isUserChiefMentorOfProjectWithTeam(principal.getId(), teamId);
	}
	@Override
	public boolean isUserChiefMentorOfProjectWithMeeting(SpmsUserDetails principal, long meetingId) {
		return userRepository.isUserChiefMentorOfProjectWithMeeting(principal.getId(), meetingId);
	}
	@Override
	public boolean isUserChiefMentorOfProjectWithMeetingFeedback(SpmsUserDetails principal, long meetingFeedbackId) {
		return userRepository.isUserChiefMentorOfProjectWithMeetingFeedback(principal.getId(), meetingFeedbackId);
	}
	@Override
	public boolean isUserChiefMentorOfProjectWithMember(SpmsUserDetails principal, long userId) {
		return userRepository.isUserChiefMentorOfProjectWithMember(principal.getId(), userId);
	}

}

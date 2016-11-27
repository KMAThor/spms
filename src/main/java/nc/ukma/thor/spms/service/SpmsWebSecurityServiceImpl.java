package nc.ukma.thor.spms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.repository.UserRepository;

@Service("spmsWebSecurityService")
public class SpmsWebSecurityServiceImpl implements SpmsWebSecurityService{
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public boolean isUserChiefMentorOfProject(String email, long projectId) {
		return userRepository.isUserChiefMentorOfProject(email, projectId);
	}
	@Override
	public boolean isUserMentorFromProject(String email, long projectId) {
		return userRepository.isUserMemberOfProject(email, projectId, Role.MENTOR);
	}
	@Override
	public boolean isUserChiefMentorOfProjectWithTeam(String email, long teamId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isUserChiefMentorOfProjectWithMeeting(String email, long meetingId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isUserChiefMentorOfProjectWithMeetingFeedback(String email, long meetingFeedbackId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isUserMentorOfTeam(String email, long teamId) {
		return userRepository.isUserMemberOfTeam(email, teamId, Role.MENTOR);
	}
	@Override
	public boolean isUserMentorOfTeamWithMeeting(String email, long meetingId) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean isUserMentorOfTeamWithfMeetingFeedback(String email, long meetingFeedbackId) {
		// TODO Auto-generated method stub
		return false;
	}



}

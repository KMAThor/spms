package nc.ukma.thor.spms.repository;

import java.util.HashMap;
import java.util.List;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.entity.UserStatus;
import nc.ukma.thor.spms.util.SortingOrder;

public interface UserRepository {
	

	public User getUserById(Long id);
	public User getUserByEmail(String email);
	public List<User> getUsersByTeam(Team team);
	public User getChiefMentorByProject(long projectId);
	public HashMap<User, UserStatus> getStudentsByTeam(Team team);
	public List<User> getAllUsers();
	public List<User> getUsersPresentAtMeeting(Meeting meeting);
	
	public void changeUserStatus(long team_id, long user_id, long new_status, String new_comment);
	
	public List<User> getUsers(long start, int length, int column, SortingOrder dir, String searchString);
	public long count();
	public long countFiltered(String string);
	
	public List<User> getUsersByRole(long offset, int length, int orderBy,
									 SortingOrder order, String searchString, Role role);
	public long countUsersByRole(Role role);
	public long countUsersByRoleFiltered(Role role, String string);

	public List<User> getFreeUsersByRole(long offset, int length, int orderBy, SortingOrder order, String searchString, Role role);
	public long countFreeUsersByRole(Role role);
	public long countFreeUsersByRoleFiltered(Role role, String searchString);
	
	public boolean isUserMemberOfProject(long id, long projectId);
	public boolean isUserMemberOfTeam(long id, long teamId);
	public boolean isUserMemberOfTeamWithMeeting(long id, long meetingId);
	public boolean isUserMemberOfTeamWithMeetingFeedback(long id, long meetingFeedbackId);
	public boolean isUserMemberOfTeamWithMember(long id, long userId);
	public boolean isUserChiefMentorOfProjectWithTeam(long id, long projectId);
	public boolean isUserChiefMentorOfProject(long id, long projectId);
	public boolean isUserChiefMentorOfProjectWithMeeting(long id, long meetingId);
	public boolean isUserChiefMentorOfProjectWithMeetingFeedback(long id, long meetingFeedbackId);
	public boolean isUserChiefMentorOfProjectWithMember(long id, long userId);

	
}

package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.util.SortingOrder;

public interface UserRepository {
	

	public User getUserById(Long id);
	public User getUserByEmail(String email);
	public List<User> getUsersByTeam(Team team);
	public User getChiefMentorByProject(long projectId);
	public List<User> getActiveStudentsByTeam(Team team);
	public List<User> getAllUsers();
	public List<User> getUsersPresentAtMeeting(Meeting meeting);
	
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
	public boolean isUserMemberOfProject(String email, long projectId, Role role);
	public boolean isUserMemberOfTeam(String email, long teamId, Role role);
	public boolean isUserChiefMentorOfProject(String email, long projectId);

	
}

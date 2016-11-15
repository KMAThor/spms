package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.util.SortingOrder;

public interface UserRepository {
	

	public User getUserById(Long id);
	public User getUserByEmail(String email);
	public List<User> getUsersByTeam(Team team);
	public List<User> getAllUsers();
	public List<User> getUsersPresentAtMeeting(Meeting meeting);
	public Long count(String string);
	public List<User> getUsers(long start, int length, int column, SortingOrder dir, String search);
	public Long count();
	

}

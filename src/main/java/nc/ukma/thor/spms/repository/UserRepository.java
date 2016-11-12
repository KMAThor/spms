package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;

public interface UserRepository {
	
	public User getUserById(long id);
	public User getUserByEmail(String email);
	public List<User> getUsersByTeam(Team team);
	public List<User> getAllUsers();
	public List<User> getUsersPresentAtMeeting(Meeting meeting);
	public List<User> getUsers(long offset, long length);
	public Long count();

}

package nc.ukma.thor.spms.service;

import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.entity.Meeting;

import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.repository.UserRepositoryJdbcImpl;
import nc.ukma.thor.spms.util.SortingOrder;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
@Service
public class UserServiceImpl implements UserService {
 
	@Autowired
	private UserRepositoryJdbcImpl userRepo;
	
    @Override
    public User getUser(String email) {
    	User usr = userRepo.getUserByEmail(email);
    	return usr;
    }

	@Override
	public User getUserById(long id) {
		return userRepo.getUserById(id);
	}

	@Override
	public List<User> getUsersByTeam(Team team) {
		return userRepo.getUsersByTeam(team);
	}
	
	@Override
	public List<User> getActiveStudentsByTeam(Team team) {
		return userRepo.getActiveStudentsByTeam(team);
	}
	
	@Override
	public List<User> getAllUsers() {
		return userRepo.getAllUsers();
	}

	@Override
	public List<User> getUsersByRole(long offset, int length, int orderBy, SortingOrder order, String search, Role role) {
		return userRepo.getUsersByRole(offset, length, orderBy, order, search, role);
	}

	@Override
	public List<User> getUsersByMeeting(Meeting meeting) {
		return userRepo.getUsersPresentAtMeeting(meeting);
	}

}


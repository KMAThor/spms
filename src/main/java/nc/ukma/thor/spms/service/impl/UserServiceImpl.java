package nc.ukma.thor.spms.service.impl;

import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.entity.UserStatus;
import nc.ukma.thor.spms.repository.jdbcImpl.UserRepositoryJdbcImpl;
import nc.ukma.thor.spms.service.UserService;
import nc.ukma.thor.spms.util.SortingOrder;

import java.util.HashMap;
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
	public User getChiefMentorByProject(long projectId) {
		return userRepo.getChiefMentorByProject(projectId);
	}

	@Override
	public List<User> getUsersByTeam(Team team) {
		return userRepo.getUsersByTeam(team);
	}
	@Override
	public List<User> getMentorsByTeam(Team team) {
		return userRepo.getMentorsByTeam(team);
	}
	
	@Override
	public HashMap<User, UserStatus> getStudentsByTeam(Team team) {
		return userRepo.getStudentsByTeam(team);
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

	@Override
	public void changeUserStatus(long team_id, long user_id, long new_status, String new_comment) {
		userRepo.changeUserStatus(team_id, user_id, new_status, new_comment);
	}

	

}


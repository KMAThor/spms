package nc.ukma.thor.spms.service;

import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.repository.UserRepositoryJdbcImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
@Service
public class UserServiceImpl implements UserService {
 
	@Autowired
	private UserRepositoryJdbcImpl userRepo;
	
    @Override
    public User getUser(String login) {
    	User usr = userRepo.getUserByEmail(login);
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
	public List<User> getAllUsers() {
		return userRepo.getAllUsers();
	}

	@Override
	public List<User> getMentors() {
		return userRepo.getMentors();
	}

}


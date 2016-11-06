package nc.ukma.thor.spms.service;

import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.repository.UserRepositoryJdbcImpl;

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
 
}
//7110eda4d09e062aa5e4a390b0a572ac0d2c0220 (1234) ShaPasswordEncoder
//$2a$06$ulgPQq7Rx6RGrDrhmyDSxuwd8GNRT43QNlLrzPubeG5.ew6zz7H5G (1234) bcrypt

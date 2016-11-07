package nc.ukma.thor.spms.service;

import java.util.List;

import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;

public interface UserService {
 
    User getUser(String login);
    User getUserById(long id);
    List<User> getUsersByTeam(Team team);
}
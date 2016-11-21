package nc.ukma.thor.spms.service;

import java.util.List;

import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.util.SortingOrder;

public interface UserService {
 
    User getUser(String login);
    User getUserById(long id);
    List<User> getUsersByTeam(Team team);
    List<User> getActiveStudentsByTeam(Team team);
    List<User> getUsersByMeeting(Meeting meeting);
    List<User> getAllUsers();

    List<User> getUsersByRole(long offset, int length, int orderBy, SortingOrder order, String search, Role role);
}
package nc.ukma.thor.spms.service;

import java.util.HashMap;
import java.util.List;

import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.entity.UserStatus;
import nc.ukma.thor.spms.util.SortingOrder;

public interface UserService {
 
	public User getUser(String login);
    public User getUserById(long id);
    public List<User> getUsersByTeam(Team team);
    public User getChiefMentorByProject(long projectId);
    public HashMap<User, UserStatus> getStudentsByTeam(Team team);
    public List<User> getUsersByMeeting(Meeting meeting);
    public List<User> getAllUsers();
    void changeUserStatus(long team_id, long user_id, long new_status, String new_comment);


    public List<User> getUsersByRole(long offset, int length, int orderBy, SortingOrder order, String search, Role role);
}
package nc.ukma.thor.spms.repository;

import java.util.List;
import java.util.Map;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;

public interface MeetingRepository {
	
	public void add(Meeting meeting);
	public void update(Meeting meeting);
	public void delete(Meeting meeting);
	
	/*Mark user presence at meeting*/
	public void addUserToMeeting(User user, Meeting meeting);
	public void deleteUserFromMeeting(User user, Meeting meeting);
	
	public Meeting getById(long id);
	public List<Meeting> getMeetingsByTeam(Team team);
	public List<User> getUsersPresentAtMeeting(Meeting meeting);

}

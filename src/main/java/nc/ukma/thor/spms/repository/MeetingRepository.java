package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;

public interface MeetingRepository extends MyRepository<Meeting>{

	/*Mark user presence at meeting*/
	public void addUserToMeeting(User user, Meeting meeting);
	public void deleteUserFromMeeting(User user, Meeting meeting);
	
	public List<Meeting> getMeetingsByTeam(Team team);
	public List<User> getUsersPresentAtMeeting(Meeting meeting);
	
}

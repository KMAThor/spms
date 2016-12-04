package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;

public interface MeetingRepository extends MyRepository<Meeting>{

	/*Mark user presence at meeting*/
	public void addUserToMeeting(Long userId, Long meetingId);
	public void deleteUserFromMeeting(Long userId, Long meetingId);
	
	public List<Meeting> getMeetingsByTeam(Long teamId);
	public List<User> getUsersPresentAtMeeting(Long meetingId);
	
	public Meeting getWithParticipantsById(long meetingId);
	public boolean isProjectCompleted(long meetingId);
	
	public default void addUserToMeeting(User user, Meeting meeting){
		addUserToMeeting(user.getId(), meeting.getId());
	}
	public default void deleteUserFromMeeting(User user, Meeting meeting){
		deleteUserFromMeeting(user.getId(), meeting.getId());
	}
	
	public default List<Meeting> getMeetingsByTeam(Team team){
		return getMeetingsByTeam(team.getId());
	}
	public default List<User> getUsersPresentAtMeeting(Meeting meeting){
		return getUsersPresentAtMeeting(meeting.getId());
	}
	
}

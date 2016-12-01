package nc.ukma.thor.spms.service;

import java.util.List;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.Team;

public interface MeetingService extends Service<Meeting>{
	
	List<Meeting> getMeetingsByTeam(Team team);
	
	void addUserToMeeting(long userId, long meetingId);
    void deleteUserFromMeeting(long userId, long meetingId);
	
    Meeting getWithParticipantsById(long meetingId);
}

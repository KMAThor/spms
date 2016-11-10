package nc.ukma.thor.spms.service;

import nc.ukma.thor.spms.entity.*;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLEngineResult.Status;

/*
TeamService interface describes team functionality
*/

public interface TeamService extends Service<Team>{
	
    //team name
    String getTeamName(long teamId);
    
    //team project
    Project getProject(long teamId);
    
    //team members
    void addMember(User user, Team team);
    void deleteMember(User user, Team team);
    
    User getMember(long teamId, long userId);
    Map<User, Status> getMembers(long teamId);
    
    //team meetings
    boolean addMeeting(long teamId, long meetingId);
    boolean deleteMeeting(long teamId, long meetingId);
    
    Meeting getMeeting(long teamId, long meetingId);
    List<Meeting> getMeetings(long teamId);
    
    List<Team> getTeamsByProject(Project project);
    
    //team info
    String getInfo(long teamId);
	
}

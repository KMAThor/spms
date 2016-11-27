package nc.ukma.thor.spms.service;

import nc.ukma.thor.spms.entity.*;
import java.util.List;

/*
TeamService interface describes team functionality
*/

public interface TeamService extends Service<Team>{
    
    //team members
    void addMember(User user, Team team);
    void deleteMember(User user, Team team);
    
    Team getFullTeamById(long team_id);
    
    List<Team> getTeamsByProject(Project project);

	public Team getActiveTeamByUser(User user);
	
}

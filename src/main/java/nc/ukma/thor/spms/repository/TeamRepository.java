package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.entity.UserStatus;

public interface TeamRepository extends MyRepository<Team>{
	public Team getActiveTeamByUser(Long id);
	public List<Team> getTeamsByUser(Long userId);
	public List<Team> getTeamsByProject(Long projectId);
	public void addUserToTeam(Long userId, Long teamId);
	public void deleteUserFromTeam(Long userId, Long teamId);
	public void changeUserStatusInTeam(Long userId, Long teamId, UserStatus userStatus);
	public UserStatus getUserStatusInTeam(Long userId, Long teamId);
	
	public Team getFullTeamById(Long teamId);

	public default List<Team> getTeamsByUser(User user){
		return getTeamsByUser(user.getId());
	}
	public default List<Team> getTeamsByProject(Project project){
		return getTeamsByProject(project.getId());
	}
	public default void addUserToTeam(User user, Team team){
		addUserToTeam(user.getId(), team.getId());
	}
	public default void deleteUserFromTeam(User user, Team team){
		deleteUserFromTeam(user.getId(), team.getId());
	}
	public default void changeUserStatusInTeam(User user, Team team, UserStatus userStatus){
		changeUserStatusInTeam(user.getId(), team.getId(), userStatus);
	}
	public default UserStatus getUserStatusInTeam(User user, Team team){
		return getUserStatusInTeam(user.getId(), team.getId());
	}
	public default Team getActiveTeamByUser(User user){
		return getActiveTeamByUser(user.getId());
	}
}

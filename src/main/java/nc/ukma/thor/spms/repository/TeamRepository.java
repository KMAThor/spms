package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Status;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;

public interface TeamRepository extends MyRepository<Team>{

	public List<Team> getTeamsByUser(User user);
	public List<Team> getTeamsByProject(Project project);
	public void addUserToTeam(User user, Team team);
	public void deleteUserFromTeam(User user, Team team);
	public void changeUserStatusInTeam(User user, Team team, Status status);
	public Status getUserStatusInTeam(User user, Team team);
}

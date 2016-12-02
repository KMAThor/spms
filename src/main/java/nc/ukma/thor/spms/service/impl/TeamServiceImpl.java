package nc.ukma.thor.spms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.repository.TeamRepository;
import nc.ukma.thor.spms.service.TeamService;

@Service
public class TeamServiceImpl extends AbstractService<Team> implements TeamService{
	
	private TeamRepository teamRepository;

	@Autowired
	public TeamServiceImpl(TeamRepository repository) {
		super(repository);
		this.teamRepository = repository;
	}
	
	@Override
	public List<Team> getTeamsByProject(Project project){
		return teamRepository.getTeamsByProject(project);
	}
	
	@Override
	public void addMember(User user, Team team) {
		teamRepository.addUserToTeam(user, team);
	}
	
	@Override
	public void deleteMember(User user, Team team) {
		teamRepository.deleteUserFromTeam(user, team);
	}

	@Override
	public Team getActiveTeamByUser(User user) {
		return teamRepository.getActiveTeamByUser(user);
	}

	@Override
	public Team getFullTeamById(long teamId) {
		return teamRepository.getFullTeamById(teamId);
	}
	


}

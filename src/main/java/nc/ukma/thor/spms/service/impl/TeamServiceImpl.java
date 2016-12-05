package nc.ukma.thor.spms.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.repository.FileRepository;
import nc.ukma.thor.spms.repository.TeamRepository;
import nc.ukma.thor.spms.service.TeamService;
import nc.ukma.thor.spms.service.WorksWithFilesService;

@Service
public class TeamServiceImpl extends AbstractService<Team> implements TeamService, WorksWithFilesService {
	
	private TeamRepository teamRepository;
	
    @Autowired
    private FileRepository fileRepository;

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

	@Override
	public List<String> getFileNames(long teamId) {
		return fileRepository.getFilesByTeam(teamId)
				.stream()
				.map(file -> file.getPath())
				.collect(Collectors.toList());
	}
}

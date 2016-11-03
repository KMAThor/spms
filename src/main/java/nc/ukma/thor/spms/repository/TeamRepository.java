package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;

public interface TeamRepository {

	public void add(Team t);
	public void update(Team t);
	public void delete(Team t);
	
	public Team getById(long id);
	public List<Team> getTeamsByUser(User user);
	public List<Team> getTeamsByProject(Project project);
}

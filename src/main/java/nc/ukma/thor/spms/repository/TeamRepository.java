package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;

public interface TeamRepository extends MyRepository<Team>{

	public List<Team> getTeamsByUser(User user);
	public List<Team> getTeamsByProject(Project project);
}

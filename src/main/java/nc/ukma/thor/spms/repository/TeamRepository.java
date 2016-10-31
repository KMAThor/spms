package nc.ukma.thor.spms.repository;

import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Team;

public interface TeamRepository {

	public void add(Team t);
	public void update(Team t);
	public void delete(Team t);
	
	public Team getTeamById(long id);
	public Team getTeamByProject(Project project);
}

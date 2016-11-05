package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.File;
import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Team;

public interface FileRepository extends MyRepository<File>{
	
	public List<File> getFilesByProject(Project project);
	public List<File> getFilesByTeam(Team team);
	
}

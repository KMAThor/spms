package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.File;
import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Team;

public interface FileRepository {
	
	public void add(File file);
	public void update(File file);
	public void delete(File file);
	
	public File getFileById(long id);
	public List<File> getFilesByProject(Project project);
	public List<File> getFilesByTeam(Team team);

}

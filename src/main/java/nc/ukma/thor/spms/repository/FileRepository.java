package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.File;

public interface FileRepository extends MyRepository<File>{
	
	public List<File> getFilesByProject(long projectId);
	public List<File> getFilesByTeam(long teamId);
	public File getByName(String fileName);
	
}

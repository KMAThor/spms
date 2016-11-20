package nc.ukma.thor.spms.service;

import java.util.List;

import nc.ukma.thor.spms.entity.File;

public interface FileService extends Service<File>{
	
	List<File> getFilesByProject(long  projectId);
	List<File> getFilesByTeam(long teamId);
	
}

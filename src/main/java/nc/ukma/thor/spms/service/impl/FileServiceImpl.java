package nc.ukma.thor.spms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nc.ukma.thor.spms.entity.File;
import nc.ukma.thor.spms.repository.FileRepository;
import nc.ukma.thor.spms.service.FileService;

@Service
public class FileServiceImpl extends AbstractService<File> implements FileService{

	private FileRepository fileRepository;

	@Autowired
	public FileServiceImpl(FileRepository repository) {
		super(repository);
		this.fileRepository = repository;
	}
	
	@Override
	public List<File> getFilesByProject(long projectId) {
		return fileRepository.getFilesByProject(projectId);
	}

	@Override
	public List<File> getFilesByTeam(long teamId) {
		return fileRepository.getFilesByProject(teamId);
	}
	
	
}

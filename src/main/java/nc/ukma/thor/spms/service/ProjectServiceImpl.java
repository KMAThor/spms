package nc.ukma.thor.spms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.repository.MyRepository;
import nc.ukma.thor.spms.repository.ProjectRepository;

@Service
public class ProjectServiceImpl extends AbstractService<Project>{

	@Autowired
	public ProjectServiceImpl(ProjectRepository repository) {
		super(repository);
	}

}

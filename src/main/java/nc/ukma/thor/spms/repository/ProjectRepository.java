package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.Project;

public interface ProjectRepository extends MyRepository<Project>{

	public List<Project> getAllActiveProjects();
	public List<Project> getAllProjects();
}
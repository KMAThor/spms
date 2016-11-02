package nc.ukma.thor.spms.repository;

import nc.ukma.thor.spms.entity.Project;

public interface ProjectRepository {
	
	public void add(Project p);
	public void update(Project p);
	public void delete(Project p);
	
	public Project getById(long id);
}
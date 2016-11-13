package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.*;

public interface ProjectRepository extends MyRepository<Project>{

	 List<Project> getAllActiveProjects();
	 List<Project> getAllProjects();
	 void addTraitToProject(Trait trait, Project project);
	 void deleteTraitFromProject(Trait trait, Project project);

	// need to implement
	void setChiefUser(User chief, Project project);
	void deleteChiefUser(Project project);
	void addTeam(Project project, Team team);
	void deleteTeam(Project project, long teamId);
	void uploadFile(Project project, File file);
	void deleteFile(Project project, long fileId);
}
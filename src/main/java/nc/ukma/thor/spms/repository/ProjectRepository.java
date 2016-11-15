package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.*;
import nc.ukma.thor.spms.util.SortingOrder;

public interface ProjectRepository extends MyRepository<Project>{

	public List<Project> getAllActiveProjects();
	public List<Project> getAllProjects();
	public void addTraitToProject(Long traitId, Long projectId);
	public void deleteTraitFromProject(Long traitId, Long projectId);
	public int[] addTraitCategoryToProject(Short traitCategoryId, Long projectId);
	public int[] deleteTraitCategoryFromProject(Short traitCategoryId, Long projectId);
	public Long count();
	public Long count(String string);
	public List<Project> getProjects(long offset, int length, int column, SortingOrder sortingOrder, String searchValue);
	
	public default void addTraitToProject(Trait trait, Project project){
		addTraitToProject(trait.getId(), project.getId());
	}
	public default void deleteTraitFromProject(Trait trait, Project project){
		deleteTraitFromProject(trait.getId(), project.getId());
	}
	public default int[] addTraitCategoryToProject(TraitCategory traitCategory, Project project){
		return addTraitCategoryToProject(traitCategory.getId(), project.getId());
	}
	public default int[] deleteTraitCategoryFromProject(TraitCategory traitCategory, Project project){
		return deleteTraitCategoryFromProject(traitCategory.getId(), project.getId());
	}


    // need to implement
    void setChiefUser(User chief, Project project);
    void deleteChiefUser(Project project);
    void uploadFile(Project project, File file);
    void deleteFile(Project project, long fileId);
}
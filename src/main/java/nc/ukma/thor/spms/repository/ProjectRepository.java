package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.*;
import nc.ukma.thor.spms.entity.report.ProjectReport;
import nc.ukma.thor.spms.util.SortingOrder;

public interface ProjectRepository extends MyRepository<Project>{

	public List<Project> getAllActiveProjects();
	public List<Project> getAllProjects();
	public void addTraitToProject(Long traitId, Long projectId);
	public void deleteTraitFromProject(Long traitId, Long projectId);
	public int[] addTraitCategoryToProject(Short traitCategoryId, Long projectId);
	public int[] deleteTraitCategoryFromProject(Short traitCategoryId, Long projectId);
	public long count();
	public long count(String string);
	public List<Project> getProjects(long offset, int length, int column, SortingOrder sortingOrder, String searchValue);
	public long countProjectsByChiefMentor(long id);
	public long countProjectsByChiefMentor(String string, long id);
	public List<Project> getProjectsByChiefMentor(int offset, int length, int column, SortingOrder sortingOrder, String value,
			Long id);
	
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
	public Long countProjectsByMentor(Long id);
	public Long countProjectsByMentor(String value, Long id);
	public List<Project> getProjectsByMentor(int start, int length, int column, SortingOrder dir, String value,
			Long id);
	public ProjectReport getProjectReport(Long projectId);

}
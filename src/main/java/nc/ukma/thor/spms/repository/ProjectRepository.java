package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.Trait;
import nc.ukma.thor.spms.entity.TraitCategory;
import nc.ukma.thor.spms.util.SortingOrder;

public interface ProjectRepository extends MyRepository<Project>{

	public List<Project> getAllActiveProjects();
	public List<Project> getAllProjects();
	public void addTraitToProject(Trait trait, Project project);
	public void deleteTraitFromProject(Trait trait, Project project);
	public int[] addTraitCategoryToProject(TraitCategory traitCategory, Project project);
	public int[] deleteTraitCategoryFromProject(TraitCategory traitCategory, Project project);
	public Long count();
	public Long count(String string);
	public List<Project> getProjects(long offset, int length, int column, SortingOrder sortingOrder, String searchValue);
	
}
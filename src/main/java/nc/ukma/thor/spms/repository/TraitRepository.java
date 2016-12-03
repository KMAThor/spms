package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Trait;
import nc.ukma.thor.spms.entity.TraitCategory;

public interface TraitRepository extends MyRepository<Trait>{
	
	public List<Trait> getTraitsByTraitCategory(TraitCategory traitCategory);

	public List<Trait> getTraitsWithoutNamesByProject(Long projectId);
	public List<Trait> getTraitsByTraitCategoryAndProject(Short traitCategoryId, Long projectId);
	public List<Trait> getTraitsByTraitCategoryAndNotFromProject(Short traitCategoryId, Long projectId);
	public boolean isTraitUsed(Trait trait);
	public boolean isTraitUsedInProject(Trait trait, Project project);
	public void forceDelete(Trait trait);
	
	public default List<Trait> getTraitsByTraitCategoryAndProject(TraitCategory traitCategory, Project project){
		return getTraitsByTraitCategoryAndProject(traitCategory.getId(), project.getId());
	}
	public default List<Trait> getTraitsByTraitCategoryAndNotFromProject(TraitCategory traitCategory, Project project){
		return getTraitsByTraitCategoryAndNotFromProject(traitCategory.getId(), project.getId());
	}
	public default List<Trait> getTraitsWithoutNamesByProject(Project project){
		return getTraitsWithoutNamesByProject(project.getId());
	}

	

}

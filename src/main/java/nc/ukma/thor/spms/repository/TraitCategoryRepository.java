package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Trait;
import nc.ukma.thor.spms.entity.TraitCategory;

public interface TraitCategoryRepository extends MyRepository<TraitCategory>{
	
	public TraitCategory getCategoryByTrait(Long traitId);
	public List<TraitCategory> getAllCategoriesWithTraits();
	public List<TraitCategory> getAllCategoriesWithTraitsByProject(Long projectId);
	public List<TraitCategory> getAllCategoriesWithTraitsByMeeting(Long meetingId);
	public boolean isTraitCategoryUsed(TraitCategory traitCategory);
	public boolean isTraitCategoryUsedInProject(TraitCategory traitCategory, Project project);
	public void forceDelete(TraitCategory traitCategory);
	
	public default List<TraitCategory> getAllCategoriesWithTraitsByProject(Project project){
		return getAllCategoriesWithTraitsByProject(project.getId());
	}
	public default TraitCategory getCategoryByTrait(Trait trait){
		return getCategoryByTrait(trait.getId());
	}
	

}
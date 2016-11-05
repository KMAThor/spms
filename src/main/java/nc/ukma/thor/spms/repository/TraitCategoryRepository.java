package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.Trait;
import nc.ukma.thor.spms.entity.TraitCategory;

public interface TraitCategoryRepository extends MyRepository<TraitCategory>{
	
	public TraitCategory getCategoryByTrait(Trait tc);
	public List<TraitCategory> getAllCategoriesWithTraits();
}
package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.Trait;
import nc.ukma.thor.spms.entity.TraitCategory;

public interface TraitCategoryRepository {

	public void add(TraitCategory tc);
	public void update(TraitCategory tc);
	public void delete(TraitCategory tc);
	
	public TraitCategory getById(short id);
	public TraitCategory getCategoryByTrait(Trait tc);
	public List<TraitCategory> getAllCategoriesWithTraits();
}
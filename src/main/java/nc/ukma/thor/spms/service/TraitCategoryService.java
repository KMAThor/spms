package nc.ukma.thor.spms.service;

import java.util.List;

import nc.ukma.thor.spms.entity.TraitCategory;

public interface TraitCategoryService extends Service<TraitCategory>{
	
	public List<TraitCategory> getAllCategoriesWithTraits();
	
}

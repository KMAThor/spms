package nc.ukma.thor.spms.repository;

import nc.ukma.thor.spms.entity.TraitCategory;

public interface TraitCategoryRepository {

	public void add(TraitCategory tc);
	public void update(TraitCategory tc);
	public void delete(TraitCategory tc);
	
	public TraitCategory getById(Long id);
}
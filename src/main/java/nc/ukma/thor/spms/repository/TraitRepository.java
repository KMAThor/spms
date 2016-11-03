package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.Trait;
import nc.ukma.thor.spms.entity.TraitCategory;

public interface TraitRepository {
	
	public void add(Trait trait);
	public void update(Trait trait);
	public void delete(Trait trait);
	
	public Trait getById(long id);
	public List<Trait> getTraitsByTraitCategory(TraitCategory traitCategory);

}

package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.Trait;
import nc.ukma.thor.spms.entity.TraitCategory;

public interface TraitRepository extends MyRepository<Trait>{
	
	public List<Trait> getTraitsByTraitCategory(TraitCategory traitCategory);

}

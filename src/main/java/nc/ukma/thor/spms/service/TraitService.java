package nc.ukma.thor.spms.service;

import java.util.List;

import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Trait;

public interface TraitService extends Service<Trait>{
	
	public boolean isTraitUsed(Trait trait);
	public void forceDelete(Trait trait);
	public List<Trait> getTraitsWithoutNamesByProject(Project project);
}

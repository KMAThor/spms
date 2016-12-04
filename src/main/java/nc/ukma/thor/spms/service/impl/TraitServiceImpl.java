package nc.ukma.thor.spms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Trait;
import nc.ukma.thor.spms.repository.TraitRepository;
import nc.ukma.thor.spms.service.TraitService;

@Service
public class TraitServiceImpl extends AbstractService<Trait> implements TraitService{

	private TraitRepository traitRepository;
	@Autowired
	public TraitServiceImpl(TraitRepository repository) {
		super(repository);
		this.traitRepository = repository;
	}
	
	@Override
	public boolean isTraitUsed(Trait trait){
		return traitRepository.isTraitUsed(trait);
	}
	
	@Override
	public void forceDelete(Trait trait) {
		traitRepository.forceDelete(trait);
	}
	
	@Override
	public List<Trait> getTraitsWithoutNamesByProject(Project project){
		return traitRepository.getTraitsWithoutNamesByProject(project);
	}
}

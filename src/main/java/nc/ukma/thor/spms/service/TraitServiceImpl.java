package nc.ukma.thor.spms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Trait;
import nc.ukma.thor.spms.repository.TraitRepository;

@Service
public class TraitServiceImpl extends AbstractService<Trait> implements TraitService{

	private TraitRepository traitRepository;
	@Autowired
	public TraitServiceImpl(TraitRepository repository) {
		super(repository);
		this.traitRepository = repository;
	}
	
	public List<Trait> getTraitsWithoutNamesByProject(Project project){
		return traitRepository.getTraitsWithoutNamesByProject(project);
	}

}

package nc.ukma.thor.spms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nc.ukma.thor.spms.entity.Trait;
import nc.ukma.thor.spms.repository.TraitRepository;

@Service
public class TraitServiceImpl extends AbstractService<Trait> implements TraitService{

	@Autowired
	public TraitServiceImpl(TraitRepository repository) {
		super(repository);
	}

}

package nc.ukma.thor.spms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nc.ukma.thor.spms.entity.TraitCategory;
import nc.ukma.thor.spms.repository.TraitCategoryRepository;

@Service
public class TraitCategoryServiceImpl extends AbstractService<TraitCategory> implements TraitCategoryService{
	
	private TraitCategoryRepository traitRepository;
	
	@Autowired
	public TraitCategoryServiceImpl(TraitCategoryRepository traitRepository){
		super(traitRepository);
		this.traitRepository = traitRepository;
	}

	@Override
	public List<TraitCategory> getAllCategoriesWithTraits() {
		return traitRepository.getAllCategoriesWithTraits();
	}

	@Override
	public List<TraitCategory> getAllCategoriesWithTraitsByProject(long projectId) {
		return traitRepository.getAllCategoriesWithTraitsByProject(projectId);
	}

	@Override
	public List<TraitCategory> getAllCategoriesWithTraitsByMeeting(long meetingId) {
		return traitRepository.getAllCategoriesWithTraitsByMeeting(meetingId);
	}

}

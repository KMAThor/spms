package nc.ukma.thor.spms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nc.ukma.thor.spms.entity.TraitCategory;
import nc.ukma.thor.spms.repository.TraitCategoryRepository;
import nc.ukma.thor.spms.service.TraitCategoryService;

@Service
public class TraitCategoryServiceImpl extends AbstractService<TraitCategory> implements TraitCategoryService{
	
	private TraitCategoryRepository traitCategoryRepository;
	
	@Autowired
	public TraitCategoryServiceImpl(TraitCategoryRepository traitRepository){
		super(traitRepository);
		this.traitCategoryRepository = traitRepository;
	}
	@Override
	public boolean isTraitCategoryUsed(TraitCategory traitCategory){
		return traitCategoryRepository.isTraitCategoryUsed(traitCategory);
	}
	@Override
	public void forceDelete(TraitCategory traitCategory) {
		traitCategoryRepository.forceDelete(traitCategory);
	}

	@Override
	public List<TraitCategory> getAllCategoriesWithTraits() {
		return traitCategoryRepository.getAllCategoriesWithTraits();
	}

	@Override
	public List<TraitCategory> getAllCategoriesWithTraitsByProject(long projectId) {
		return traitCategoryRepository.getAllCategoriesWithTraitsByProject(projectId);
	}

	@Override
	public List<TraitCategory> getAllCategoriesWithTraitsByMeeting(long meetingId) {
		return traitCategoryRepository.getAllCategoriesWithTraitsByMeeting(meetingId);
	}
}

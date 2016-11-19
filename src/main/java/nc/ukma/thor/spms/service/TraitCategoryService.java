package nc.ukma.thor.spms.service;

import java.util.List;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.TraitCategory;

public interface TraitCategoryService extends Service<TraitCategory>{
	
	public List<TraitCategory> getAllCategoriesWithTraits();

	public List<TraitCategory> getAllCategoriesWithTraitsByProject(long projectId);

	public List<TraitCategory> getAllCategoriesWithTraitsByMeeting(long meetingId);

	public default List<TraitCategory> getAllCategoriesWithTraitsByMeeting(Meeting meeting){
		return getAllCategoriesWithTraitsByMeeting(meeting.getId());
	}
}

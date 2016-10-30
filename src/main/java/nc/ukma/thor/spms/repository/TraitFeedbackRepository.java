package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.TraitFeedback;

public interface TraitFeedbackRepository {
	
	public void add(TraitFeedback f, long appendedToId);
	public void update(TraitFeedback f);
	public void delete(TraitFeedback f);
	
	public TraitFeedback getById(long id);
	public List<TraitFeedback> getAllAppendedTo(long id);
}

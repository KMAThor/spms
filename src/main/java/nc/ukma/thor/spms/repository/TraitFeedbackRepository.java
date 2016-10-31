package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.TraitFeedback;

public interface TraitFeedbackRepository {
	
	public void add(TraitFeedback tf, long appendedToId);
	public void update(TraitFeedback tf);
	public void delete(TraitFeedback tf);
	
	public TraitFeedback getById(long id);
	public List<TraitFeedback> getAllAppendedTo(long id);
}

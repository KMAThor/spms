package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.TraitFeedback;

public interface TraitFeedbackRepository extends MyRepository<TraitFeedback>{
	
	public void add(TraitFeedback tf, Long appendedToId);

	public List<TraitFeedback> getAllAppendedTo(Long id);
}

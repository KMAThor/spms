package nc.ukma.thor.spms.entity;

import java.util.List;

/**
 * Entity which describes feedback about student
 * which mentor can leave after team meeting
 **/

public class MeetingFeedback {
	
	private long id;
	private String summary;
	private User student;
	private User author;
	private List<TraitFeedback> traitFeedbacks;
	
	public MeetingFeedback() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public User getStudent() {
		return student;
	}

	public void setStudent(User student) {
		this.student = student;
	}

	public User getAuthor() {
		return author;
	}

	public void setAuthor(User author) {
		this.author = author;
	}

	public List<TraitFeedback> getTraitFeedbacks() {
		return traitFeedbacks;
	}

	public void setTraitFeedbacks(List<TraitFeedback> traitFeedbacks) {
		this.traitFeedbacks = traitFeedbacks;
	}

}

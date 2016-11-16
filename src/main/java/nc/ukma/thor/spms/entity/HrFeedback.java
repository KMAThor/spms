package nc.ukma.thor.spms.entity;

import java.util.List;

public class HrFeedback {
	
	private Long id;
	private String topic;
	private String summary;
	private User student;
	private User added_by;
	private User author;
	private List<TraitFeedback> traitFeedbacks;
	
	public HrFeedback() {};
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
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

	public User getAdded_by() {
		return added_by;
	}

	public void setAdded_by(User added_by) {
		this.added_by = added_by;
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

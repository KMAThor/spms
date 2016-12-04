package nc.ukma.thor.spms.entity.report;


public class HrFeedbackInfo {

	private String topic;
	private String summary;
	private PersonInfo author;
	private PersonInfo added_by;
	
	public HrFeedbackInfo() {};
	
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
	public PersonInfo getAuthor() {
		return author;
	}
	public void setAuthor(PersonInfo author) {
		this.author = author;
	}
	public PersonInfo getAdded_by() {
		return added_by;
	}
	public void setAdded_by(PersonInfo added_by) {
		this.added_by = added_by;
	}

}

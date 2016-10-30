package nc.ukma.thor.spms.entity;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

public class Meeting {
	
	private long id;
	private String topic;
	private Timestamp date;
	private List<MeetingFeedback> feedbacks;
	private Map<Long, Boolean> presence;
	
	public Meeting() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public List<MeetingFeedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(List<MeetingFeedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

}

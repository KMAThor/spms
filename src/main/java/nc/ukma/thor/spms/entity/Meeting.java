package nc.ukma.thor.spms.entity;

import java.sql.Timestamp;
import java.util.List;

public class Meeting {

	private Long id;
	private String topic;
	private Timestamp startDate;
	private Team team;
	private List<MeetingFeedback> feedbacks;
	private List<User> participants;
	
	public Meeting() {}
	
	public Meeting(Long id) {
		this.id = id;
	}

	public Meeting(String topic, Timestamp startDate, Team team) {
		this.topic = topic;
		this.startDate = startDate;
		this.team = team;
	}

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

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp date) {
		this.startDate = date;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public List<MeetingFeedback> getFeedbacks() {
		return feedbacks;
	}

	public void setFeedbacks(List<MeetingFeedback> feedbacks) {
		this.feedbacks = feedbacks;
	}

	public List<User> getParticipants() {
		return participants;
	}

	public void setParticipants(List<User> participants) {
		this.participants = participants;
	}

	@Override
	public String toString() {
		return "Meeting [id=" + id + ", topic=" + topic + ", startDate=" + startDate + ", team=" + team + ", feedbacks="
				+ feedbacks + ", participants=" + participants + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Meeting other = (Meeting) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}

package nc.ukma.thor.spms.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * This entity represent feedback about student
 * which mentor can leave after team meeting
 **/

public class MeetingFeedback {
	
	private Long id;
	private String summary;
	private User student;
	private Meeting meeting;
	private User author;
	private List<TraitFeedback> traitFeedbacks = new ArrayList<TraitFeedback>();
	
	public MeetingFeedback() {}
	
	public MeetingFeedback(Long id) {
		this.id = id;
	}
	
	public MeetingFeedback(String summary, Long studentId, Long meetingId, Long authorId) {
		this.summary = summary;
		this.meeting = new Meeting(meetingId);
		this.student = new User(studentId);
		this.author = new User(authorId);
	}

	public MeetingFeedback(Long id, String summary, Long studentId, Long meetingId, Long authorId) {
		this.id = id;
		this.summary = summary;
		this.meeting = new Meeting(meetingId);
		this.student = new User(studentId);
		this.author = new User(authorId);
	}

	public MeetingFeedback(String summary, Long studentId, Long meetingId, User user) {
		this.summary = summary;
		this.meeting = new Meeting(meetingId);
		this.student = new User(studentId);
		this.author = user;
	}
	
	public MeetingFeedback(Long id, String summary, Long studentId, Long meetingId, User user) {
		this.id = id;
		this.summary = summary;
		this.meeting = new Meeting(meetingId);
		this.student = new User(studentId);
		this.author = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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


	public Meeting getMeeting() {
		return meeting;
	}

	public void setMeeting(Meeting meeting) {
		this.meeting = meeting;
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
	

	public void addTraitFeedback(TraitFeedback traitFeedback) {
		traitFeedbacks.add(traitFeedback);
	}


	@Override
	public String toString() {
		return "MeetingFeedback [id=" + id + ", summary=" + summary + ", student=" + student
				 + ", meetingId=" + meeting.getId() + ", author=" + author + ", traitFeedbacks=" + traitFeedbacks + "]";
	}

}

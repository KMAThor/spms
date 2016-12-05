package nc.ukma.thor.spms.entity.report;

import java.sql.Timestamp;

public class MeetingTraitFeedbackInfo {
	
	private String meetingTopic;
	private Timestamp startDate;
	private PersonInfo mentor;
	private String comment;
	private short score;
	
	public MeetingTraitFeedbackInfo(){}

	public String getMeetingTopic() {
		return meetingTopic;
	}

	public void setMeetingTopic(String meetingTopic) {
		this.meetingTopic = meetingTopic;
	}

	public Timestamp getStartDate() {
		return startDate;
	}

	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}

	public PersonInfo getMentor() {
		return mentor;
	}

	public void setMentor(PersonInfo mentor) {
		this.mentor = mentor;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public short getScore() {
		return score;
	}

	public void setScore(short score) {
		this.score = score;
	}
	
}

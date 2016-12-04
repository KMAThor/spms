package nc.ukma.thor.spms.entity.report;

import java.sql.Timestamp;
import java.util.List;

public class MeetingTraitFeedbackInfo {
	
	private String meetingTopic;
	private Timestamp startDate;
	private List<MentorTraitFeedbackInfo> mentorsTraitFeedbackInfo;
	
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
	public List<MentorTraitFeedbackInfo> getMentorsTraitFeedbackInfo() {
		return mentorsTraitFeedbackInfo;
	}
	public void setMentorsTraitFeedbackInfo(List<MentorTraitFeedbackInfo> mentorsTraitFeedbackInfo) {
		this.mentorsTraitFeedbackInfo = mentorsTraitFeedbackInfo;
	}
}

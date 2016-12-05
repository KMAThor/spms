package nc.ukma.thor.spms.entity.report;

import java.util.ArrayList;
import java.util.List;

public class TraitInfo {

	private String name;
	private double averageScore;
	private List<MeetingTraitFeedbackInfo> meetingsTraitFeedbackInfo = new ArrayList<>();;
	
	public TraitInfo(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getAverageScore() {
		return averageScore;
	}

	public void setAverageScore(double averageScore) {
		this.averageScore = averageScore;
	}

	public List<MeetingTraitFeedbackInfo> getMeetingsTraitFeedbackInfo() {
		return meetingsTraitFeedbackInfo;
	}

	public void setMeetingsTraitFeedbackInfo(List<MeetingTraitFeedbackInfo> meetingsTraitFeedbackInfo) {
		this.meetingsTraitFeedbackInfo = meetingsTraitFeedbackInfo;
	}
	
}

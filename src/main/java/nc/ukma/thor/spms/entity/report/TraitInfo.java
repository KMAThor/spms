package nc.ukma.thor.spms.entity.report;

import java.util.ArrayList;
import java.util.List;

public class TraitInfo {

	private String name;
	private double averageScore = 0;
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

	public List<MeetingTraitFeedbackInfo> getMeetingsTraitFeedbackInfo() {
		return meetingsTraitFeedbackInfo;
	}

	public void setMeetingsTraitFeedbackInfo(List<MeetingTraitFeedbackInfo> meetingsTraitFeedbackInfo) {
		double averageScore = 0;
		int numberOfScores = 0;
		for(MeetingTraitFeedbackInfo meetingTraitFeedbackInfo: meetingsTraitFeedbackInfo){
			int score = meetingTraitFeedbackInfo.getScore();
			if(score != 0) {
				averageScore += score;
				numberOfScores++;
			}
		}
		if(numberOfScores != 0) this.averageScore = averageScore/numberOfScores;
		this.meetingsTraitFeedbackInfo = meetingsTraitFeedbackInfo;
	}
	
}

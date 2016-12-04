package nc.ukma.thor.spms.entity.report;

import java.util.ArrayList;
import java.util.List;

public class ProjectReport {

	private ProjectInfo projectInfo;
	private int numberOfParticipants;
	private int numberOfParticipantsWhoCompletedSuccessfully;
	private int numberOfParticipantsWhoLeft;
	private int numberOfParticipantsWhomInterviewWasScheduled;
	private int numberOfParticipantsWhoGotJobOffer;
	private List<PersonInfo> participantsWhoLeft = new ArrayList<>();
	private List<String> reasonsWhy = new ArrayList<>();
	
	public ProjectReport(){}
	
	public ProjectInfo getProjectInfo() {
		return projectInfo;
	}
	public void setProjectInfo(ProjectInfo projectInfo) {
		this.projectInfo = projectInfo;
	}
	public int getNumberOfParticipants() {
		return numberOfParticipants;
	}
	public void setNumberOfParticipants(int numberOfParticipants) {
		this.numberOfParticipants = numberOfParticipants;
	}
	public int getNumberOfParticipantsWhoCompletedSuccessfully() {
		return numberOfParticipantsWhoCompletedSuccessfully;
	}
	public void setNumberOfParticipantsWhoCompletedSuccessfully(int numberOfParticipantsWhoCompletedSuccessfully) {
		this.numberOfParticipantsWhoCompletedSuccessfully = numberOfParticipantsWhoCompletedSuccessfully;
	}
	public int getNumberOfParticipantsWhoLeft() {
		return numberOfParticipantsWhoLeft;
	}
	public void setNumberOfParticipantsWhoLeft(int numberOfParticipantsWhoLeft) {
		this.numberOfParticipantsWhoLeft = numberOfParticipantsWhoLeft;
	}
	public int getNumberOfParticipantsWhomInterviewWasScheduled() {
		return numberOfParticipantsWhomInterviewWasScheduled;
	}
	public void setNumberOfParticipantsWhomInterviewWasScheduled(int numberOfParticipantsWhomInterviewWasScheduled) {
		this.numberOfParticipantsWhomInterviewWasScheduled = numberOfParticipantsWhomInterviewWasScheduled;
	}
	public int getNumberOfParticipantsWhoGotJobOffer() {
		return numberOfParticipantsWhoGotJobOffer;
	}
	public void setNumberOfParticipantsWhoGotJobOffer(int numberOfParticipantsWhoGotJobOffer) {
		this.numberOfParticipantsWhoGotJobOffer = numberOfParticipantsWhoGotJobOffer;
	}
	public List<PersonInfo> getParticipantsWhoLeft() {
		return participantsWhoLeft;
	}
	public void setParticipantsWhoLeft(List<PersonInfo> participantsWhoLeft) {
		this.participantsWhoLeft = participantsWhoLeft;
	}
	public List<String> getReasonsWhy() {
		return reasonsWhy;
	}
	public void setReasonsWhy(List<String> reasonsWhy) {
		this.reasonsWhy = reasonsWhy;
	}
	
	public void addParticipantsWhoLeftAndReasonWhy(PersonInfo personInfo, String reason){
		participantsWhoLeft.add(personInfo);
		reasonsWhy.add(reason);
	}
		
}
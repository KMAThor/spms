package nc.ukma.thor.spms.entity.report;

public class ProjectReport {

	private int numberOfParticipants;
	private int numberOfParticipantsWhoLeft;
	private int numberOfParticipantsWhomInterviewWasScheduled;
	private int numberOfParticipantsWhoGotJobOffer;
	
	public ProjectReport() {} 
	
	public ProjectReport(int numberOfParticipants, int numberOfParticipantsWhoLeft,
			int numberOfParticipantsWhomInterviewWasScheduled, int numberOfParticipantsWhoGotJobOffer) {
		this.numberOfParticipants = numberOfParticipants;
		this.numberOfParticipantsWhoLeft = numberOfParticipantsWhoLeft;
		this.numberOfParticipantsWhomInterviewWasScheduled = numberOfParticipantsWhomInterviewWasScheduled;
		this.numberOfParticipantsWhoGotJobOffer = numberOfParticipantsWhoGotJobOffer;
	}
	
	public int getNumberOfParticipants() {
		return numberOfParticipants;
	}

	public void setNumberOfParticipants(int numberOfParticipants) {
		this.numberOfParticipants = numberOfParticipants;
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

}
package nc.ukma.thor.spms.entity.report;

public class MentorTraitFeedbackInfo {

	private PersonInfo mentor;
	private String comment;
	private short score;
	
	public MentorTraitFeedbackInfo() {}

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

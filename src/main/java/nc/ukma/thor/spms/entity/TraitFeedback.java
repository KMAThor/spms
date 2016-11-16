package nc.ukma.thor.spms.entity;

public class TraitFeedback {
	
	private Long id;
	private int score;
	private String comment;
	private Trait trait;
	
	public TraitFeedback() {}
	
	public TraitFeedback(int score, String comment, Trait trait) {
		this.score = score;
		this.comment = comment;
		this.trait = trait;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public Trait getTrait() {
		return trait;
	}

	public void setTrait(Trait trait) {
		this.trait = trait;
	}

	@Override
	public String toString() {
		return "TraitFeedback [id=" + id + ", score=" + score + ", comment=" + comment + ", trait=" + trait + "]";
	}

}

package nc.ukma.thor.spms.entity;

public class TraitFeedback {
	
	private Long id;
	private short score;
	private String comment;
	private Trait trait;
	
	public TraitFeedback() {}
	
	public TraitFeedback(short score, String comment, Long traitId) {
		this.score = score;
		this.comment = comment;
		this.trait = new Trait(traitId);
	}

	public TraitFeedback(Long id, short score, String comment, Long traitId) {
		this.id = id;
		this.score = score;
		this.comment = comment;
		this.trait = new Trait(traitId);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public short getScore() {
		return score;
	}

	public void setScore(short score) {
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
	
	public boolean isEmpty() {
		return (score == 0 && comment.isEmpty());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TraitFeedback other = (TraitFeedback) obj;
		if (trait == null) {
			if (other.trait != null)
				return false;
		} else if (!trait.equals(other.trait))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TraitFeedback [id=" + id + ", score=" + score + ", comment=" + comment + ", trait=" + trait + "]";
	}

}

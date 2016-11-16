package nc.ukma.thor.spms.entity;

public class Status {
	
	private Short id;
	private String status;
	private String comment;
	
	public Status() {}
	
	public Status(Short id){
		this.id = id;
	}
	
	public Status(Short id, String comment) {
		this.id = id;
		this.comment = comment;
	}

	public Short getId() {
		return id;
	}
	public void setId(Short id) {
		this.id = id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	};
	
	public static enum Statuses {}

	@Override
	public String toString() {
		return "Status [id=" + id + ", status=" + status + ", comment=" + comment + "]";
	}

}

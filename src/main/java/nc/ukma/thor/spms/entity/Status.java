package nc.ukma.thor.spms.entity;

public class Status {
	
	private short id;
	private String status;
	private String comment;
	
	public Status() {}
	
	public Status(short id){
		this.id = id;
	}
	
	public Status(short id, String comment) {
		this.id = id;
		this.comment = comment;
	}

	public short getId() {
		return id;
	}
	public void setId(short id) {
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

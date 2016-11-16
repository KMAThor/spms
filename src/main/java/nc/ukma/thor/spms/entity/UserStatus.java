package nc.ukma.thor.spms.entity;

public class UserStatus {

	private Status status;
	private String comment;
	
	public UserStatus(Status status){
		this.status  = status;
	}
	
	public UserStatus(Status status, String comment){
		this.status  = status;
		this.comment = comment;
	}
	
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	@Override
	public String toString() {
		return "UserStatus [status=" + status + ", comment=" + comment + "]";
	}
}

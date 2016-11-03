package nc.ukma.thor.spms.entity;

public class Status {
	
	private short id;
	private String status;
	
	public Status() {}
	
	public Status(short id){
		this.id = id;
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
	
	public static enum Statuses {};
}

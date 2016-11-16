package nc.ukma.thor.spms.entity;

public enum Status {
	
	ACTIVE("active"),
	LEFT_PROJECT("left_project"),
	INTERVIEW_WAS_SCHEDULED("interview_was_scheduled"),
	GOT_JOB_OFFER("got_job_offer");
	
	private Short id;
	private String name;
	
	private Status(String name) {
		this.name = name;
	}
	
	public Short getId() {
		return id;
	}
	public void setId(Short id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
		
	@Override
	public String toString() {
		return "Status [id=" + id + ", name=" + name + "]";
	}

}

package nc.ukma.thor.spms.entity;

public enum Role {
	
	ADMIN("admin"), MENTOR("mentor"), HR("hr"), STUDENT("student");
	
	private Short id;
	private String name;
	
	private Role(String name) {
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
		return "Role [id=" + id + ", name=" + name + "]";
	}
	
}

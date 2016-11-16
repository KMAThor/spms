package nc.ukma.thor.spms.entity;

public class Role {
	
	private Short id;
	private String name;
	
	public Role() {}

	public Role(Short id) {
		this.id = id;
	}

	public Role(Short id, String name) {
		this.id = id;
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
	
	public static enum Roles { ADMIN, MENTOR, HR, STUDENT};
}

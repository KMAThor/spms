package nc.ukma.thor.spms.entity;

public class Role {
	
	private short id;
	private String name;
	
	public Role() {}

	public Role(short id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = (short) id;
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

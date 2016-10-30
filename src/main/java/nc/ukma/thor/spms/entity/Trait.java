package nc.ukma.thor.spms.entity;

public class Trait {

	private long id;
	private String name;
	
	public Trait() {}

	public Trait(long id) {
		this.id = id;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

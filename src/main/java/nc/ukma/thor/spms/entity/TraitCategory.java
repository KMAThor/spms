package nc.ukma.thor.spms.entity;

import java.util.List;

public class TraitCategory {
	
	private long id;
	private String name;
	private List<Trait> traits;
	
	public TraitCategory() {}

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

	public List<Trait> getTraits() {
		return traits;
	}

	public void setTraits(List<Trait> traits) {
		this.traits = traits;
	}
	
}

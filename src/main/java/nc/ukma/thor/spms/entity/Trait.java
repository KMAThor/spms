package nc.ukma.thor.spms.entity;

public class Trait {

	private long id;
	private String name;
	private TraitCategory traitCategory;
	
	public Trait() { }

	public Trait(long id) {
		this.id = id;
	}
	
	public Trait(String name, TraitCategory traitCategory) {
		this.name = name;
		this.traitCategory = traitCategory;
	}

	

	public Trait(long id, String name) {
		this.id = id;
		this.name = name;
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

	public TraitCategory getTraitCategory() {
		return traitCategory;
	}

	public void setTraitCategory(TraitCategory traitCategory) {
		this.traitCategory = traitCategory;
	}

	@Override
	public String toString() {
		return "Trait [id=" + id + ", name=" + name + ", traitCategory=" + traitCategory + "]";
	}
}

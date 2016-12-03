package nc.ukma.thor.spms.entity;

public class Trait {

	private Long id;
	private String name;
	private TraitCategory traitCategory;
	
	public Trait() { }

	public Trait(Long id) {
		this.id = id;
	}
	
	public Trait(String name, TraitCategory traitCategory) {
		this.name = name;
		this.traitCategory = traitCategory;
	}

	

	public Trait(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public Trait(Long id, String name, TraitCategory traitCategory) {
		this.id = id;
		this.name = name;
		this.traitCategory = traitCategory;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trait other = (Trait) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Trait [id=" + id + ", name=" + name + ", traitCategory=" + traitCategory + "]";
	}
}

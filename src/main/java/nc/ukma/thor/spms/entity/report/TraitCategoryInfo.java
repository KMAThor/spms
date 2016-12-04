package nc.ukma.thor.spms.entity.report;

import java.util.List;

public class TraitCategoryInfo {

	private String name;
	private List<TraitInfo> traitsInfo;
	
	public TraitCategoryInfo() {}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<TraitInfo> getTraitInfo() {
		return traitsInfo;
	}
	public void setTraitInfo(List<TraitInfo> traitsInfo) {
		this.traitsInfo = traitsInfo;
	}
	
}

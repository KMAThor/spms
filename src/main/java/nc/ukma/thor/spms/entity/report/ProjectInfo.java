package nc.ukma.thor.spms.entity.report;

import java.sql.Timestamp;

public class ProjectInfo {
	
	private String name;
	private String description;
	private Timestamp startDate;
	private Timestamp endDate;
	private boolean isCompleted;
	
	public ProjectInfo() {}
	
	public ProjectInfo(String name, String description, Timestamp startDate, Timestamp endDate, boolean isCompleted) {
		super();
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isCompleted = isCompleted;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Timestamp getStartDate() {
		return startDate;
	}
	public void setStartDate(Timestamp startDate) {
		this.startDate = startDate;
	}
	public Timestamp getEndDate() {
		return endDate;
	}
	public void setEndDate(Timestamp endDate) {
		this.endDate = endDate;
	}
	public boolean isCompleted() {
		return isCompleted;
	}
	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	
}

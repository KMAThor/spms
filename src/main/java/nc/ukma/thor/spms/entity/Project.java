package nc.ukma.thor.spms.entity;

import java.sql.Timestamp;
import java.util.List;

public class Project {
	
	private long id;
	private String name;
	private String description;
	private Timestamp startDate;
	private Timestamp endDate;
	private boolean isCompleated;
	private User chiefUser;
	private List<Team> teams;
	private List<File> files;
	private List<Trait> traits;
	
	public Project() { }

	public Project(String name, String description, Timestamp startDate, Timestamp endDate, boolean isCompleated,
			User chiefUser) {
		this.name = name;
		this.description = description;
		this.startDate = startDate;
		this.endDate = endDate;
		this.isCompleated = isCompleated;
		this.chiefUser = chiefUser;
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

	public boolean isCompleated() {
		return isCompleated;
	}

	public void setCompleated(boolean isCompleated) {
		this.isCompleated = isCompleated;
	}

	public User getChiefUser() {
		return chiefUser;
	}

	public void setChiefUser(User chiefUser) {
		this.chiefUser = chiefUser;
	}

	public List<Team> getTeams() {
		return teams;
	}

	public void setTeams(List<Team> teams) {
		this.teams = teams;
	}

	public List<File> getFiles() {
		return files;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}

	public List<Trait> getTraits() {
		return traits;
	}

	public void setTraits(List<Trait> traits) {
		this.traits = traits;
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", description=" + description + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", isCompleated=" + isCompleated + ", chiefUser=" + chiefUser + ", teams="
				+ teams + ", files=" + files + ", traits=" + traits + "]";
	}
	
}

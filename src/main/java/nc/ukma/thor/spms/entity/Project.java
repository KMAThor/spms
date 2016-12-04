package nc.ukma.thor.spms.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Project{
	
	private Long id;
	private String name;
	private String description;
	private Timestamp startDate;
	private Timestamp endDate;
	private boolean isCompleted;
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
		this.isCompleted = isCompleated;
		this.chiefUser = chiefUser;
	}

	public Project(Long id) {
		this.id = id;
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
	public boolean	getIsCompleted() {
		return isCompleted;
	}

	public void setIsCompleted(boolean isCompleated) {
		this.isCompleted = isCompleated;
	}

	public User getChiefMentor() {
		return chiefUser;
	}

	public void setChiefMentor(User chiefUser) {
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

	public boolean userHasAccessToFile(User user, String fileName) {
		return user.getRole() == Role.ADMIN || (hasUser(user) && files.stream().anyMatch(f -> f.getName() == fileName));
	}

	public boolean hasUser(User user) {
		return chiefUser.getId() == user.getId() || getTeams()
			.stream()
			.map(team -> team
				.getMembers()
				.keySet()
				.stream()
				.map(member -> member.getId())
				.anyMatch(id -> id == user.getId()))
			.anyMatch(bool -> bool);
	}

	@Override
	public String toString() {
		return "Project [id=" + id + ", name=" + name + ", description=" + description + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", isCompleted=" + isCompleted + ", chiefUser=" + chiefUser + ", teams="
				+ teams + ", files=" + files + ", traits=" + traits + "]";
	}
	
}

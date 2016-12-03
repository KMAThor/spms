package nc.ukma.thor.spms.entity;

import java.util.List;
import java.util.Map;

public class Team {

	private Long id;
	private String name;
	private String linkToRepo;
	private Project project;
	private Map<User, UserStatus> members;
	private List<Meeting> meetings;
	private List<File> files;
	
	public Team() {}

	public Team(Long id) {
		this.id = id;
	}
	
	public Team(String name, Project project) {
		this.name = name;
		this.project = project;
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

	public void setLinkToRepo(String linkToRepo) {
		this.linkToRepo = linkToRepo;
	}
	
	public String getLinkToRepo(String linkToRepo) {
		return linkToRepo;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Map<User, UserStatus> getMembers() {
		return members;
	}

	public void setMembers(Map<User, UserStatus> members) {
		this.members = members;
	}

	public List<Meeting> getMeetings() {
		return meetings;
	}

	public void setFiles(List<File> files) {
		this.files = files;
	}
	
	public List<File> getFiles() {
		return files;
	}

	public void setMeetings(List<Meeting> meetings) {
		this.meetings = meetings;
	}

	@Override
	public String toString() {
		return "Team [id=" + id + ", name=" + name + ", project=" + project + ", members=" + members + ", meetings="
				+ meetings + "]";
	}

}

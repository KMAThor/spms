package nc.ukma.thor.spms.entity;

import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLEngineResult.Status;

public class Team {

	private long id;
	private String name;
	private Project project;
	private Map<User, Status> members;
	private List<Meeting> meetings;
	
	public Team() {}

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

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public Map<User, Status> getMembers() {
		return members;
	}

	public void setMembers(Map<User, Status> members) {
		this.members = members;
	}

	public List<Meeting> getMeetings() {
		return meetings;
	}

	public void setMeetings(List<Meeting> meetings) {
		this.meetings = meetings;
	}
}

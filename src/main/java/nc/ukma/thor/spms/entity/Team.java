package nc.ukma.thor.spms.entity;

import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLEngineResult.Status;

public class Team {

	private long id;
	private String name;
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

	public List<Meeting> getMeetings() {
		return meetings;
	}

	public void setMeetings(List<Meeting> meetings) {
		this.meetings = meetings;
	}
}

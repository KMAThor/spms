package nc.ukma.thor.spms.service;

import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLEngineResult.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.repository.TeamRepository;

@Service
public class TeamServiceImpl extends AbstractService<Team> implements TeamService{
	
	private TeamRepository teamRepository;

	@Autowired
	public TeamServiceImpl(TeamRepository repository) {
		super(repository);
		this.teamRepository = repository;
	}
	
	@Override
	public List<Team> getTeamsByProject(Project project){
		return teamRepository.getTeamsByProject(project);
	}

	@Override
	public String getTeamName(long teamId) {
		return null;
	}

	@Override
	public Project getProject(long teamId) {
		return null;
	}

	@Override
	public boolean addMember(long teamId, long userId) {
		return false;
	}

	@Override
	public boolean addMembers(long teamId, Map<User, Status> members) {
		return false;
	}

	@Override
	public boolean deleteMember(long teamId, long userId) {
		return false;
	}

	@Override
	public User getMember(long teamId, long userId) {
		return null;
	}

	@Override
	public Map<User, Status> getMembers(long teamId) {
		return null;
	}

	@Override
	public boolean addMeeting(long teamId, long meetingId) {
		return false;
	}

	@Override
	public boolean addMeetings(long teamId, List<Meeting> meetings) {
		return false;
	}

	@Override
	public boolean deleteMeeting(long teamId, long meetingId) {
		return false;
	}

	@Override
	public Meeting getMeeting(long teamId, long meetingId) {
		return null;
	}

	@Override
	public List<Meeting> getMeetings(long teamId) {
		return null;
	}

	@Override
	public String getInfo(long teamId) {
		return null;
	}

}

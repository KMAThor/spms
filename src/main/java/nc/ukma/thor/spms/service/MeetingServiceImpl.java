package nc.ukma.thor.spms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.repository.MeetingRepository;

@Service
public class MeetingServiceImpl extends AbstractService<Meeting> implements MeetingService{
	
	private MeetingRepository meetingRepository;
	
	@Autowired
	public MeetingServiceImpl(MeetingRepository repository) {
		super(repository);
		this.meetingRepository = repository;
	}

	@Override
	public List<Meeting> getMeetingsByTeam(Team team) {
		return meetingRepository.getMeetingsByTeam(team);
	}

	@Override
	public void addUserToMeeting(long user_id, long meeting_id) {
		meetingRepository.addUserToMeeting(user_id, meeting_id);
	}

	@Override
	public void deleteUserFromMeeting(long user_id, long meeting_id) {
		meetingRepository.deleteUserFromMeeting(user_id, meeting_id);
	}

	@Override
	public Meeting getWithParticipantsById(long meetingId) {
		return meetingRepository.getWithParticipantsById(meetingId);
	}

}

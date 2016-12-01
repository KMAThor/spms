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
	public void addUserToMeeting(long userId, long meetingId) {
		meetingRepository.addUserToMeeting(userId, meetingId);
	}

	@Override
	public void deleteUserFromMeeting(long userId, long meetingId) {
		meetingRepository.deleteUserFromMeeting(userId, meetingId);
	}

	@Override
	public Meeting getWithParticipantsById(long meetingId) {
		return meetingRepository.getWithParticipantsById(meetingId);
	}

}

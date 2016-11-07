package nc.ukma.thor.spms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.repository.MeetingRepository;
import nc.ukma.thor.spms.repository.MeetingRepositoryJdbcImpl;

public class MeetingServiceImpl implements MeetingService{
	
	private MeetingRepository meetingRepository;
	
	@Autowired
	public MeetingServiceImpl(MeetingRepository repository) {
		this.meetingRepository = repository;
	}

	@Override
	public List<Meeting> getMeetingsByTeam(Team team) {
		return meetingRepository.getMeetingsByTeam(team);
	}

}

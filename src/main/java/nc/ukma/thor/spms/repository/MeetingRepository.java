package nc.ukma.thor.spms.repository;

import java.util.List;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.Team;

public interface MeetingRepository {
	
	public void add(Meeting meeting);
	public void update(Meeting meeting);
	public void delete(Meeting meeting);
	
	public Meeting getById(long id);
	public List<Meeting> getMeetingsByTeam(Team team);

}

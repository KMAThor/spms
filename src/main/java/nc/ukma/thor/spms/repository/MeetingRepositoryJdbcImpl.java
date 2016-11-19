package nc.ukma.thor.spms.repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;

@Repository
public class MeetingRepositoryJdbcImpl implements MeetingRepository{
	
	private static final String INSERT_MEETING_SQL = "INSERT INTO meeting (topic, start_date, team_id) VALUES(?,?,?);";
	private static final String UPDATE_MEETING_SQL = "UPDATE meeting SET topic=?, start_date=?, team_id=? WHERE id = ?;";
	private static final String DELETE_MEETING_SQL = "DELETE FROM meeting WHERE id = ?;";
	private static final String ADD_USER_TO_MEETING_SQL = "INSERT INTO presence (user_id, meeting_id) VALUES(?,?);";
	private static final String DELETE_USER_FROM_MEETING_SQL = "DELETE FROM presence WHERE user_id=? AND meeting_id=?;";
	private static final String GET_MEETING_BY_ID_SQL = "SELECT * FROM meeting WHERE id = ?;";
	private static final String GET_MEETINGS_BY_TEAM_SQL = "SELECT * FROM meeting WHERE team_id = ?;";
	private static final String GET_PRESENCE_FOR_MEETING_SQL = "SELECT * FROM presence WHERE meeting_id = ?;";
	
	private static final RowMapper<Meeting> MEETING_MAPPER = new MeetingMapper();
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public void add(Meeting m) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection ->{
			PreparedStatement ps = connection.prepareStatement(INSERT_MEETING_SQL, new String [] {"id"});
			ps.setString(1, m.getTopic());
			ps.setTimestamp(2, m.getStartDate());
			ps.setLong(3, m.getTeam().getId());
			return ps;
		}, keyHolder);
		m.setId(keyHolder.getKey().longValue());
	}

	@Override
	public void update(Meeting m) {
		jdbcTemplate.update(UPDATE_MEETING_SQL,
				new Object [] {m.getTopic(), m.getStartDate(), m.getTeam().getId(), m.getId()});	
	}

	@Override
	public void delete(Meeting m) {
		jdbcTemplate.update(DELETE_MEETING_SQL, m.getId());
	}
	
	@Override
	public void addUserToMeeting(Long userId, Long meetingId) {
		jdbcTemplate.update(ADD_USER_TO_MEETING_SQL, userId, meetingId);
	}
	
	@Override
	public void deleteUserFromMeeting(Long userId, Long meetingId) {
		jdbcTemplate.update(DELETE_USER_FROM_MEETING_SQL, userId, meetingId);
	}

	@Override
	public Meeting getById(Long id) {
		try{
			return jdbcTemplate.queryForObject(GET_MEETING_BY_ID_SQL,
					new Object[] {id}, MEETING_MAPPER);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
	}
	
	@Override
	public List<Meeting> getMeetingsByTeam(Long teamId) {
			return jdbcTemplate.query(GET_MEETINGS_BY_TEAM_SQL,
					new Object[] { teamId }, MEETING_MAPPER);
	}

	@Override
	public List<User> getUsersPresentAtMeeting(Long meetingId) {
		return jdbcTemplate.query(GET_PRESENCE_FOR_MEETING_SQL,
				new Object [] { meetingId },
				(rs, rowNum) -> {
					User user = new User(rs.getLong("user_id"));
					return user;
					});
	}

	private static final class MeetingMapper implements RowMapper<Meeting>{
		@Override
		public Meeting mapRow(ResultSet rs, int rowNumber) throws SQLException{
			Meeting meeting = new Meeting();
			meeting.setId(rs.getLong("id"));
			meeting.setTopic(rs.getString("topic"));
			meeting.setStartDate(rs.getTimestamp("start_date"));
			meeting.setTeam(new Team(rs.getLong("team_id")));
			return meeting;
		}
	}
}

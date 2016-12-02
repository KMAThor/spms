package nc.ukma.thor.spms.repository;

import nc.ukma.thor.spms.entity.File;
import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class FileRepositoryJdbcImpl implements FileRepository {
	
	private static final String INSERT_FILE_SQL = "INSERT INTO file (path, project_id, team_id) VALUES(?, ?, ?);";
	private static final String UPDATE_FILE_SQL = "UPDATE file SET path=?, project_id=?, team_id=? WHERE id = ?;";
	private static final String DELETE_FILE_SQL = "DELETE FROM file WHERE id = ?;";
	private static final String GET_FILE_BY_ID_SQL = "SELECT * FROM file WHERE id = ?;";
	private static final String GET_FILES_BY_PROJECT_SQL = "SELECT * FROM file WHERE project_id = ?;";
	private static final String GET_FILES_BY_TEAM_SQL = "SELECT * FROM file WHERE team_id = ?;";
	
	private static final RowMapper<File> FILE_MAPPER = new FileMapper();
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
    @Override
    public void add(File f) {
    	KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(INSERT_FILE_SQL, new String[] {"id"});
			ps.setString(1, f.getPath());
			if(f.getProject() != null) ps.setLong(2, f.getProject().getId());
			else ps.setNull(2, java.sql.Types.BIGINT);
			if(f.getTeam() != null) ps.setLong(3, f.getTeam().getId());
			else ps.setNull(3, java.sql.Types.BIGINT);
			return ps;
		}, keyHolder);
		f.setId(keyHolder.getKey().longValue());
    }

    @Override
    public void update(File f) {
    	Object [] values = { f.getPath(), null, null };
    	if(f.getProject() != null) values[1] = f.getProject().getId();
    	if(f.getTeam() != null) values[2] = f.getTeam().getId();
		jdbcTemplate.update(UPDATE_FILE_SQL, values);
    }

    @Override
    public void delete(File f) {
    	jdbcTemplate.update(DELETE_FILE_SQL, f.getId());
    }

    @Override
    public File getById(Long id) {
    	try{
			return jdbcTemplate.queryForObject(GET_FILE_BY_ID_SQL,
						new Object[] { id },
						FILE_MAPPER);
		}catch(EmptyResultDataAccessException e){
			return null;
		}
    }

    @Override
    public List<File> getFilesByProject(long projectId) {
    	return jdbcTemplate.query(GET_FILES_BY_PROJECT_SQL,
				new Object[] {projectId}, FILE_MAPPER);
    }

    @Override
    public List<File> getFilesByTeam(long teamId) {
    	return jdbcTemplate.query(GET_FILES_BY_TEAM_SQL,
				new Object[] {teamId}, FILE_MAPPER);
    }
    
    private static final class FileMapper implements RowMapper<File>{
		@Override
		public File mapRow(ResultSet rs, int rowNum) throws SQLException {
			File file = new File();
			file.setId(rs.getLong("id"));
			file.setPath(rs.getString("path"));
			Long projectId = rs.getLong("project_id");
			if(!rs.wasNull()) file.setProject(new Project(projectId));
			Long teamId = rs.getLong("team_id");
			if(!rs.wasNull()) file.setTeam(new Team(teamId));
			return file;
		}
	}
}

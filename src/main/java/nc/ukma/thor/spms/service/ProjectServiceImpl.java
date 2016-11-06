package nc.ukma.thor.spms.service;

import nc.ukma.thor.spms.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nc.ukma.thor.spms.repository.MyRepository;
import nc.ukma.thor.spms.repository.ProjectRepository;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ProjectServiceImpl extends AbstractService<Project> implements ProjectService{

	@Autowired
	public ProjectServiceImpl(ProjectRepository repository) {
		super(repository);
	}

    @Override
    public boolean changeProjectName(long projectId, String newProjectName) {
        return false;
    }

    @Override
    public boolean setTimeStamp(long projectId, boolean start, Timestamp date) {
        return false;
    }

    @Override
    public boolean setDescription(long projectId, String description) {
        return false;
    }

    @Override
    public boolean deleteDescription(long projectId) {
        return false;
    }

    @Override
    public boolean setChiefUser(long projectId, User chief) {
        return false;
    }

    @Override
    public boolean deleteChiefUser(long projectId) {
        return false;
    }

    @Override
    public boolean addTeams(long projectId, List<Team> team) {
        return false;
    }

    @Override
    public boolean changeTeamName(long projectId, long teamId, String newName) {
        return false;
    }

    @Override
    public boolean deleteTeam(long projectId, long teamId) {
        return false;
    }

    @Override
    public Team getTeam(long projectId, long teamId) {
        return null;
    }

    @Override
    public List<Team> getAllTeams(long projectId) {
        return null;
    }

    @Override
    public boolean uploadFile(long projectId, File file) {
        return false;
    }

    @Override
    public boolean deleteFile(long projectId, long fileId) {
        return false;
    }

    @Override
    public File getFile(long projectId, long fileId) {
        return null;
    }

    @Override
    public boolean setTraits(long projectId, List<Trait> traits) {
        return false;
    }

    @Override
    public boolean setTrait(long projectId, Trait trait) {
        return false;
    }

    @Override
    public boolean deleteTrait(long projectId, long traitId) {
        return false;
    }

    @Override
    public boolean deleteTraits(long projectId, List<Trait> traits) {
        return false;
    }

    @Override
    public Trait getTrait(long projectId, long traitId) {
        return null;
    }

    @Override
    public List<Trait> getTraits(long projectId) {
        return null;
    }

    @Override
    public String getInfo(long projectId) {
        return null;
    }
}

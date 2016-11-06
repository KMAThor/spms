package nc.ukma.thor.spms.service;

import nc.ukma.thor.spms.entity.*;

import java.sql.Timestamp;
import java.util.List;

public interface ProjectService{

    boolean changeProjectName(long projectId, String newProjectName);

    // timestamp
    boolean setTimeStamp(long projectId, boolean start, Timestamp date);

    //description of the project
    boolean setDescription(long projectId, String description);
    boolean deleteDescription(long projectId);

    // chief mentor
    boolean setChiefUser(long projectId, User chief);
    boolean deleteChiefUser(long projectId);


    // team functionality
    boolean addTeams(long projectId, List<Team> team);
    boolean changeTeamName(long projectId, long teamId, String newName);
    boolean deleteTeam(long projectId, long teamId);

    Team getTeam(long projectId, long teamId);
    List<Team> getAllTeams(long projectId);

    // file functionality at the project
    boolean uploadFile(long projectId, File file);
    boolean deleteFile(long projectId, long fileId);
    File getFile(long projectId, long  fileId);

    // traits
    boolean setTraits(long projectId, List<Trait> traits);
    boolean setTrait(long projectId, Trait trait);

    boolean deleteTrait(long projectId, long traitId);
    boolean deleteTraits(long projectId, List<Trait> traits);
    Trait getTrait(long projectId, long traitId);
    List<Trait> getTraits(long projectId);

    String getInfo(long projectId);
}

package nc.ukma.thor.spms.service;

import nc.ukma.thor.spms.entity.*;

import java.sql.Timestamp;
import java.util.List;

public interface ProjectService {
/*
 ProjectService interface describes all functionality for projects
 */
    boolean createProject(Project project);
    boolean deleteProject(long projectId);
    boolean changeProjectName(long projectId, String newProjectName);

    // timestamp
    boolean setTimeStamp(long projectId, boolean start, Timestamp date);
    boolean changeTimeStamp(long projectId, boolean start, Timestamp date);

    //description of the project
    boolean setDescription(long projectId, String description);
    boolean changeDescription(long projectId, String descritpion);
    boolean deleteDescription(long projectId);

    // chief mentor
    boolean setChiefUser(long projectId, User chief);
    boolean deleteChiefUser(long projectId);
    boolean changeChiefUser(long projectId, User newChief);


    // team functionality
    boolean addTeam(long projectId, Team team);
    boolean addTeams(long projectId, List<Team> team);
    boolean changeTeamName(long projectId, long teamId, String newName);
    boolean deleteTeam(long projectId, long teamId);

    Team getTeam(long projectId, long teamId);
    List<Team> getAllTeams(long projectId);

    // user
    boolean addUser(long projectId, long teamId, User user);
    boolean addUsers(long projectId, List<User> users);
    boolean deleteUser(long projectId, long Id, String user);

    User getUser(long projectId, String user);
    List<User> getAllUsers(long projectId);

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

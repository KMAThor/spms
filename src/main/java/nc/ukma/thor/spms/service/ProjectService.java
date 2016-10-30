package nc.ukma.thor.spms.service;

import nc.ukma.thor.spms.entity.*;

import java.sql.Timestamp;
import java.util.List;

public interface ProjectService {
/*
 ProjectService interface describes all functionality for projects
 */
    boolean createProject(long projectId, String project);
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
    boolean changeTeamName(long projectId, String team, String newName);
    boolean deleteTeam(long projectId, String team);

    Team getTeam(long projectId, String team);
    List<Team> getAllTeams(long projectId);

    // user
    boolean addUser(long projectId, Team team, User user);
    boolean addUsers(long projectId, List<User> team);
    boolean deleteUser(long projectId, Team team, String user);

    User getUser(long projectId, String user);
    List<User> getAllUsers(long projectId);

    // file functionality at the project
    boolean uploadFile(long projectId, File file);
    boolean deleteFile(long projectId, String file);
    File getFile(long projectId, String file);

    // traits
    boolean setTraits(long projectId, List<Trait> traits);
    boolean setTrait(long projectId, Trait trait);

    boolean deleteTrait(long projectId, Trait trait);
    boolean deleteTraits(long projectId, List<Trait> traits);
    Trait getTrait(long projectId, long traitId);
    List<Trait> getTraits(long projectId);

    String getInfo(long projectId);
}

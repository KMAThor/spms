package nc.ukma.thor.spms.service;

import nc.ukma.thor.spms.entity.File;
import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

public interface ProjectService {
/*
 ProjectService interface describes all functionality for projects
 */
    boolean createProject(String project);
    boolean deleteProject(String project);
    boolean changeProjectName(String project, String newProjectName);

    // timestamp
    boolean setTimeStamp(String project, boolean start, Timestamp date);
    boolean changeTimeStamp(String project, boolean start, Timestamp date);

    //description of the project
    boolean setDescription(String project, String description);
    boolean changeDescription(String project, String descritpion);
    boolean deleteDescription(String project);

    // chief mentor
    boolean setChiefUser(String project, User chief);
    boolean deleteChiefUser(String project);
    boolean changeChiefUser(String project, User newChief);


    // team functionality
    boolean addTeam(String project, Team team);
    boolean changeTeamName(String project, String team, String newName);
    boolean deleteTeam(String project, String team);
    boolean addUser(String project, String team, String user);
    boolean deleteUser(String project, String team, String user);
    Team getTeam(String project, String team);
    List<Team> getAllTeams(String project);

    // file functionality at the project
    boolean uploadFile(String project, String file);
    boolean deleteFile(String project, String file);
    File getFile(String project, String file);

    // traits


    String getInfo(String project);
}

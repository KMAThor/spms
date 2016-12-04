package nc.ukma.thor.spms.service;

import nc.ukma.thor.spms.entity.*;
import nc.ukma.thor.spms.entity.report.ProjectReport;
import nc.ukma.thor.spms.entity.report.StudentReport;

import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

public interface ProjectService extends Service<Project>{

	public ProjectReport getProjectReport(Project project);
	public Workbook getProjectReportInXlsFormat(Project project);
	
    // chief mentor
    boolean setChiefUser(long projectId, User chief);
    boolean deleteChiefUser(long projectId);

    // team functionality
    boolean addTeam(Team team);
    boolean deleteTeam(long projectId, long teamId);

    Team getTeam(long projectId, long teamId);
    List<Team> getAllTeams(long projectId);

    // traits
    boolean setTraits(long projectId, List<Trait> traits);
    boolean setTrait(long projectId, Trait trait);

    boolean deleteTrait(long projectId, long traitId);
    boolean deleteTraits(long projectId, List<Trait> traits);
    Trait getTrait(long projectId, long traitId);
    List<Trait> getTraits(long projectId);

    List <Project> getAllActiveProjects();
    Project getProject(long projectId);

    String getInfo(long projectId);
}


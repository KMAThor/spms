package nc.ukma.thor.spms.service.impl;

import nc.ukma.thor.spms.entity.*;
import nc.ukma.thor.spms.entity.report.ProjectReport;
import nc.ukma.thor.spms.repository.*;
import nc.ukma.thor.spms.service.ProjectService;
import nc.ukma.thor.spms.service.ReportService;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl extends AbstractService<Project> implements ProjectService{

    @Autowired
	private ProjectRepository projectRepository;
    @Autowired
	private ReportService reportService;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TraitRepository traitRepository;
	
    @Autowired
	public ProjectServiceImpl(ProjectRepository repository) {
		super(repository);
		this.projectRepository = repository;
	}


    @Override
    public boolean setChiefUser(long projectId, User chief) {
        Project project = projectRepository.getById(projectId);
        projectRepository.setChiefUser(chief, project);
        return true;
    }

    @Override
    public boolean deleteChiefUser(long projectId) {
        projectRepository.deleteChiefUser(projectRepository.getById(projectId));
        return true;
    }

    @Override
    public boolean addTeam(Team team) {
        teamRepository.add(team);
        return true;
    }

    @Override
    public boolean deleteTeam(long projectId,  long teamId) {
       teamRepository.delete(getTeam(projectId, teamId));
        return true;
    }

    @Override
    public Team getTeam(long projectId, long teamId) {

        List<Team> teams = getAllTeams(projectId);
        for(Team team: teams){
            if (team.getId() == teamId){
                return team;
            }
        }

        return null;
    }

    @Override
    public List<Team> getAllTeams(long projectId) {

        return teamRepository.getTeamsByProject(projectRepository.getById(projectId));
    }

    @Override
    public boolean setTraits(long projectId, List<Trait> traits) {
        return false;
    }


    @Override
    public boolean setTrait(long projectId, Trait trait) {
        projectRepository.addTraitToProject(trait, projectRepository.getById(projectId));
        return true;
    }

    @Override
    public boolean deleteTrait(long projectId, long traitId) {

        projectRepository.deleteTraitFromProject(new Trait(traitId), projectRepository.getById(projectId));
        return true;
    }

    @Override
    public boolean deleteTraits(long projectId, List<Trait> traits) {
        return false;
    }


    @Override
    public Trait getTrait(long projectId, long traitId) {

        List<Trait> traits = getTraits(projectId);
        for(Trait trait: traits){
            if (trait.getId() == traitId){
                return trait;
            }
        }
        return null;
    }

    @Override
    public List<Trait> getTraits(long projectId) {
        return traitRepository.getTraitsWithoutNamesByProject(projectRepository.getById(projectId));
    }

    @Override
    public List<Project> getAllActiveProjects() {
        return projectRepository.getAllActiveProjects();
    }

    @Override
    public Project getProject(long projectId) {

        List<Project> projects = getAllActiveProjects();
        for(Project project: projects){
            if(project.getId() == projectId){
                return project;
            }
        }

        return null;
    }


    @Override
    public String getInfo(long projectId) {
        return null;
    }
    
	@Override
	public ProjectReport getProjectReport(Project project) {
		return projectRepository.getProjectReport(project.getId());
	}

	@Override
	public Workbook getProjectReportInXlsFormat(Project project) {
		return reportService.projectReportToWorkbook(getProjectReport(project));
	}


	@Override
	public List<Project> getProjectsByUser(long userId) {
		return projectRepository.getProjectsByUser(userId);
	}
}

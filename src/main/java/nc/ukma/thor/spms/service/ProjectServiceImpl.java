package nc.ukma.thor.spms.service;

import nc.ukma.thor.spms.entity.*;
import nc.ukma.thor.spms.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ProjectServiceImpl extends AbstractService<Project> implements ProjectService{

	private ProjectRepository projectRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private FileRepository fileRepository;

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
    public boolean addTeam(long projectId, Team team) {
        projectRepository.addTeam(projectRepository.getById(projectId), team);
        return true;
    }

    @Override
    public boolean deleteTeam(long projectId, long teamId) {
        projectRepository.deleteTeam(projectRepository.getById(projectId), teamId);
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
    public boolean uploadFile(long projectId, File file) {

        projectRepository.uploadFile(projectRepository.getById(projectId), file);
        return true;
    }

    @Override
    public boolean deleteFile(long projectId, long fileId) {

        projectRepository.deleteFile(projectRepository.getById(projectId), fileId);
        return true;
    }

    @Override
    public File getFile(long projectId, long fileId) {

        List<File> files = fileRepository.getFilesByProject(projectRepository.getById(projectId));
        for (File file: files){
            if(file.getId() == fileId){
                return file;
            }
        }
        return null;
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
}

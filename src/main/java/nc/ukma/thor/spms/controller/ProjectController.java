package nc.ukma.thor.spms.controller;

import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.repository.UserRepository;
import nc.ukma.thor.spms.service.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.util.LinkedList;

@Controller
public class ProjectController {

    @Autowired
    public ProjectServiceImpl projectServiceImpl;

    @ResponseBody
    @RequestMapping(path="/testApi/project/", method = RequestMethod.POST)
    public Project testProject(){
        Project project = new Project("training", "for the best students/n and only for them", new Timestamp(1234L),
                new Timestamp(2345L), false, new User(1231242342L));
        return project;
    }

    @ResponseBody
    @RequestMapping(path="/project/create/", method = RequestMethod.POST)
    public Project createProject(@RequestParam long id ){

        Project newProject = new Project(id);
        projectServiceImpl.create(newProject);
        return newProject;
    }

    @ResponseBody
    @RequestMapping(path="/project/create/add_team/", method = RequestMethod.POST)
    public String addTeam(@RequestParam long projectId, @RequestParam Team team){

        LinkedList<Team> teams = new LinkedList<>();
        teams.add(team);
        projectServiceImpl.addTeams(projectId, teams);
        return "ok";
    }

    @ResponseBody
    @RequestMapping(path="/project/delete/", method= RequestMethod.POST)
    public String deleteProject (@RequestParam long projectId){

        Project project = projectServiceImpl.getById(projectId);
        projectServiceImpl.delete(project);
        return "ok";
    }

    @ResponseBody
    @RequestMapping(path= "/project/update/", method = RequestMethod.POST)
    public String updateProject(Project update){

        projectServiceImpl.update(update);
        return "ok";
    }
}

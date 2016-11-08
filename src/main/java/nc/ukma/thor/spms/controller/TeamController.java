package nc.ukma.thor.spms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.service.ProjectService;
import nc.ukma.thor.spms.service.TeamService;
import nc.ukma.thor.spms.service.UserService;
import nc.ukma.thor.spms.util.DateUtil;

@Controller
public class TeamController {
	
	@Autowired
    private ProjectService projectService;
	@Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;
    //@Autowired
    //private MeetingService meetingService;

    @RequestMapping(path="/{project_id}/create/team/", method = RequestMethod.POST)
    public String createTeam(@PathVariable long project_id, HttpServletRequest request, Model model){
    	Project project = projectService.getById(project_id);
    	Team team = new Team(request.getParameter("name"), project);
    	teamService.create(team);
    	model.addAttribute("team", team);
        return "team";
    }
	
	@RequestMapping(path="/view/team/{id}", method = RequestMethod.GET)
    public String viewTeam(@PathVariable long id, Model model ){
    	Team team = teamService.getById(id);
    	model.addAttribute("team", team);
    	model.addAttribute("students", userService.getUsersByTeam(team));
    	//model.addAttribute("meetings", meetingService.getMeetingsByTeam(team));
        return "team";
    }
	
	@RequestMapping(path="/update/team/{id}/", method = RequestMethod.POST)
    public String updateProject(@PathVariable long id, HttpServletRequest request, Model model ){
		Team team = teamService.getById(id);
    	team.setName(request.getParameter("name"));
    	teamService.update(team);
    	model.addAttribute("team", team);
        return "team";
    }
	
	@RequestMapping(path="/delete/team/{id}/", method = RequestMethod.GET)
    public String deleteProject(@PathVariable long id){
		Team team = teamService.getById(id);
		long projectId = team.getProject().getId();
    	team = new Team(id);
    	teamService.delete(team);
        return "redirect:/view/project/" + projectId + "/";
    }

}

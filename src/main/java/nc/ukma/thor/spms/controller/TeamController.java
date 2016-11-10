package nc.ukma.thor.spms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.service.ProjectService;
import nc.ukma.thor.spms.service.TeamService;
import nc.ukma.thor.spms.service.UserService;

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
	
	@RequestMapping(path="/view/team/{id}/", method = RequestMethod.GET)
    public String viewTeam(@PathVariable long id, Model model ){
    	Team team = teamService.getById(id);
    	model.addAttribute("team", team);
    	model.addAttribute("users", userService.getUsersByTeam(team));
    	model.addAttribute("all_users", userService.getAllUsers());
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
	
	@RequestMapping(path="/{team_id}/addUser/{user_id}/", method = RequestMethod.GET)
    public String addUser(@PathVariable long team_id, @PathVariable long user_id){
		Team team = teamService.getById(team_id);
    	User user = userService.getUserById(user_id);
    	teamService.addMember(user, team);
    	return "redirect:/view/team/" + team_id + "/";
    }
	
	@RequestMapping(path="/{team_id}/deleteUser/{user_id}/", method = RequestMethod.GET)
    public String deleteUser(@PathVariable long team_id, @PathVariable long user_id){
		Team team = teamService.getById(team_id);
    	User user = userService.getUserById(user_id);
    	teamService.deleteMember(user, team);
    	return "redirect:/view/team/" + team_id + "/";
    }

}

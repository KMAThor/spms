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
import nc.ukma.thor.spms.service.MeetingService;
import nc.ukma.thor.spms.service.TeamService;
import nc.ukma.thor.spms.service.UserService;

@Controller
public class TeamController {
	
	@Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;
    @Autowired
    private MeetingService meetingService;

    @RequestMapping(path="/create/team/{project_id}", method = RequestMethod.POST)
    public String createTeam(@PathVariable long project_id, HttpServletRequest request){
    	Project project = new Project(project_id);
    	Team team = new Team(request.getParameter("name"), project);
    	teamService.create(team);
        return "redirect:/view/team/" + team.getId() + "/";
    }
    
	@RequestMapping(path="/update/team/{team_id}", method = RequestMethod.POST)
    public String updateProject(@PathVariable long team_id, HttpServletRequest request){
    	Team team = new Team(team_id);
    	team.setName(request.getParameter("name"));
    	teamService.update(team);
    	return "redirect:/view/team/" + team.getId() + "/";
    }
	
	@RequestMapping(path="/delete/team/{team_id}", method = RequestMethod.POST)
    public String deleteProject(@PathVariable long team_id, HttpServletRequest request){
    	Team team = new Team(team_id);
    	teamService.delete(team);
    	long projectId = Long.valueOf(request.getParameter("project_id"));
        return "redirect:/view/project/" + projectId + "/";
    }
	
	@RequestMapping(path="/view/team/{team_id}/", method = RequestMethod.GET)
    public String viewTeam(@PathVariable long team_id, Model model ){
    	Team team = teamService.getById(team_id);
    	model.addAttribute("team", team);
    	model.addAttribute("users", userService.getUsersByTeam(team));
    	model.addAttribute("all_users", userService.getAllUsers());
    	model.addAttribute("meetings", meetingService.getMeetingsByTeam(team));
        return "team";
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

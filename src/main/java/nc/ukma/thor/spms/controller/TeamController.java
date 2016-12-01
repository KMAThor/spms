package nc.ukma.thor.spms.controller;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.mail.EmailSender;
import nc.ukma.thor.spms.service.TeamService;
import nc.ukma.thor.spms.service.UserService;

@Controller
@RequestMapping("/team/")
public class TeamController {
	
	@Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;
    
    @ResponseBody
    @RequestMapping(path="/create/", method = RequestMethod.POST)
    public Long createTeam(@RequestParam long projectId, @RequestParam String name){
    	Project project = new Project(projectId);
    	Team team = new Team(name, project);
    	teamService.create(team);
        return team.getId();
    }
    
    @ResponseBody
    @RequestMapping(path="/update/", method = RequestMethod.POST)
    public String updateProject(@RequestParam long teamId, @RequestParam String name){
    	Team team = new Team(teamId);
    	team.setName(name);
    	teamService.update(team);
    	return "success";
    }
	
    @ResponseBody
	@RequestMapping(path="/delete/", method = RequestMethod.POST)
    public String deleteProject(@RequestParam long teamId){
    	Team team = new Team(teamId);
    	teamService.delete(team);
        return "success";
    }
	
	@RequestMapping(path="/view/{teamId}/", method = RequestMethod.GET)
    public String viewTeam(@PathVariable long teamId, Model model ){
    	Team team = teamService.getFullTeamById(teamId);
    	model.addAttribute("team", team);
        return "team";
    }
	
	@ResponseBody
	@RequestMapping(path="/addUser/", method = RequestMethod.POST)
    public User addUser(@RequestParam long userId, @RequestParam long teamId){
		Team team = teamService.getById(teamId);
    	User user = userService.getUserById(userId);
    	
    	List<User> usersToNotify = new ArrayList<>();
    	usersToNotify.add(user);
    	EmailSender.sendMessageUserAddedToProject(usersToNotify, team.getName());
    	
    	teamService.addMember(user, team);
    	return user;
    }
	
	@ResponseBody
	@RequestMapping(path="/deleteUser/", method = RequestMethod.POST)
    public String deleteUser(@RequestParam long userId, @RequestParam long teamId){
		Team team = new Team(teamId);
    	User user = new User(userId);
    	teamService.deleteMember(user, team);
    	return "success";
    }

}

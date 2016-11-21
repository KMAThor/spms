package nc.ukma.thor.spms.controller;


import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
import nc.ukma.thor.spms.service.FileService;
import nc.ukma.thor.spms.service.MeetingService;
import nc.ukma.thor.spms.service.TeamService;
import nc.ukma.thor.spms.service.UserService;

@Controller
public class TeamController {
	
	@Autowired
    private TeamService teamService;
	@Autowired
    private MeetingService meetingService;
	@Autowired
    private FileService fileService;
    @Autowired
    private UserService userService;
    @ResponseBody
    @RequestMapping(path="/team/create/", method = RequestMethod.POST)
    public String createTeam(@RequestParam long project_id, @RequestParam String name){
    	Project project = new Project(project_id);
    	Team team = new Team(name, project);
    	teamService.create(team);
        return "/spms/view/team/" + team.getId() + "/";
    }
    
    @ResponseBody
    @RequestMapping(path="/team/update/", method = RequestMethod.POST)
    public String updateProject(@RequestParam long id, @RequestParam String name){
    	Team team = new Team(id);
    	team.setName(name);
    	teamService.update(team);
    	return "success";
    }
	
    @ResponseBody
	@RequestMapping(path="/team/delete/", method = RequestMethod.POST)
    public String deleteProject(@RequestParam long id){
    	Team team = new Team(id);
    	teamService.delete(team);
        return "success";
    }
	
	@RequestMapping(path="/team/view/{team_id}/", method = RequestMethod.GET)
    public String viewTeam(@PathVariable long team_id, Model model ){
    	Team team = teamService.getById(team_id);
    	model.addAttribute("team", team);
    	model.addAttribute("meetings", meetingService.getMeetingsByTeam(team));
    	model.addAttribute("files", fileService.getFilesByTeam(team_id));
    	model.addAttribute("users", userService.getUsersByTeam(team));
        return "team";
    }
	
	@RequestMapping(path="/{team_id}/addUser/{user_id}/", method = RequestMethod.GET)
    public String addUser(@PathVariable long team_id, @PathVariable long user_id){
		Team team = teamService.getById(team_id);
    	User user = userService.getUserById(user_id);
    	List<User> usersToNotify = new ArrayList<>();
    	usersToNotify.add(user);
    	EmailSender.sendMessageUserAddedToProject(usersToNotify, team.getName());
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

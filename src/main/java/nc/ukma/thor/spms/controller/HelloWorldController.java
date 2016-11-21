package nc.ukma.thor.spms.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.mail.EmailSender;
import nc.ukma.thor.spms.repository.ProjectRepository;
import nc.ukma.thor.spms.service.ProjectService;
import nc.ukma.thor.spms.service.TeamService;
import nc.ukma.thor.spms.service.UserService;

@Controller
public class HelloWorldController {
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private TeamService teamService;

	
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String sayHello(ModelMap model, Principal principal) {
    	User user = userService.getUser(principal.getName());
    	/*Temp solution*/
    	boolean isCheifMentor = false;
    	if(projectRepository.countProjectsByChiefMentor(user.getId()) != 0) {
    		isCheifMentor = true;
    	}
    	if(user.getRole() == Role.MENTOR && !isCheifMentor){
    		Team team = teamService.getActiveTeamByUser(user);
			return "redirect:/team/view/" + team.getId() + "/";
    	}

    	return "index";

    }
    
}
package nc.ukma.thor.spms.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.repository.ProjectRepository;
import nc.ukma.thor.spms.service.TeamService;
import nc.ukma.thor.spms.service.UserService;

@Controller
public class HelloWorldController {
	@Autowired
	private ProjectRepository projectRepository;
	@Autowired
	private UserService userService;
	@Autowired
	private TeamService teamService;

	
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String sayHello(ModelMap model, Principal principal) {
    	/*Temp solution*/
    	/*User user = userService.getUser(principal.getName());
    	boolean isCheifMentor = false;
    	if(projectRepository.countProjectsByChiefMentor(user.getId()) != 0) {
    		isCheifMentor = true;
    	}
    	if(user.getRole() == Role.MENTOR && !isCheifMentor){
    		Team team = teamService.getActiveTeamByUser(user);
			return "redirect:/team/view/" + team.getId() + "/";
    	}*/

    	return "index";

    }
    @RequestMapping(value="/something/", method = RequestMethod.GET)
    public String justForTest(ModelMap model, Principal principal) {
    
    return "index";

    }
    
    @RequestMapping(value="/403/", method = RequestMethod.GET)
    public String accessDiniedErrorPage(ModelMap model, Principal principal) {
    	model.addAttribute("httpStatus", HttpStatus.FORBIDDEN.value());
		model.addAttribute("message", "Access denied");
		return "error";
    }
    
    @RequestMapping(value="/404/", method = RequestMethod.GET)
    public String notFound(ModelMap model, Principal principal) {
    	model.addAttribute("httpStatus", HttpStatus.NOT_FOUND.value());
		model.addAttribute("message", "Such page does not exist");
		return "error";
    }
    
    @RequestMapping(value="/databaseError/", method = RequestMethod.GET)
    public String databaseErrorPage(ModelMap model, Principal principal) {
    	model.addAttribute("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR.value());
    	model.addAttribute("message", "Database error, please try again later.");
		return "error";
    }
    
    @RequestMapping(value="/500/", method = RequestMethod.GET)
    public String internalServerError(ModelMap model, Principal principal) {
		model.addAttribute("httpStatus", HttpStatus.INTERNAL_SERVER_ERROR.value());
		model.addAttribute("message", "INTERNAL SERVER ERROR");
		return "unknownError";
    }
        
}
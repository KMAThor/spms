package nc.ukma.thor.spms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.service.ProjectServiceImpl;

@Controller
@RequestMapping("test/repository/")
public class RepositoriesTestController {
	
	@Autowired
	private ProjectServiceImpl projectService;
	
	@RequestMapping(value="project/", method = RequestMethod.GET)
    public String testProjectRepository(ModelMap model) {
    
    	
        model.addAttribute("greeting", "Hello World!");

    	Project p =  projectService.getById(4);
    	System.out.println(p);
        return "index";
    }
	/*System.out.println(roleRepository.getRoleById(0));
	System.out.println(roleRepository.getRoleByName("hr"));
	User user = new User();
	user.setId(0);
	System.out.println(roleRepository.getRoleByUser(user));
	
	System.out.println(userRepository.getUserById(0));
	System.out.println(userRepository.getUserByEmail("hr@hr.com"));
	
	Team team = new Team();
	team.setId(5);
	System.out.println(userRepository.getUserByTeam(team));
	 
	Calendar c = Calendar.getInstance();
	c.set(2016, 10, 1, 10, 0);
	System.out.println(c);
	Timestamp t = new Timestamp(c.getTime().getTime());
	Project p = new Project("Super project3", "Some description3", t, t, false, new User(1));
	p.setId(1001);
	Project p2 =  projectRepository.getById(1001);
	System.out.println(p2);
	projectRepository.delete(p2);
	p2 =  projectRepository.getById(1001);
	System.out.println(p2);*/
}

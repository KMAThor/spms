package nc.ukma.thor.spms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import nc.ukma.thor.spms.service.ProjectService;

@Controller
public class HelloWorldController {
	@Autowired
	private ProjectService projectService;

	
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String sayHello(ModelMap model) {
    	model.addAttribute("projects", projectService.getAllActiveProjects());
        model.addAttribute("greeting", "Hello World!");

        return "index";
    }
    
    @RequestMapping(value="/project/{id}/", method = RequestMethod.GET)
    public String viewProject(@PathVariable("id") int id, ModelMap model) {
    	System.out.println(id);
    	
        model.addAttribute("greeting", "Hello World!");

        return "index";
    }

  
}
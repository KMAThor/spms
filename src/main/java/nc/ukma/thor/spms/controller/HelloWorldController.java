package nc.ukma.thor.spms.controller;

import java.sql.Timestamp;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.Trait;
import nc.ukma.thor.spms.entity.TraitFeedback;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.repository.ProjectRepository;
import nc.ukma.thor.spms.repository.RoleRepository;
import nc.ukma.thor.spms.repository.TraitFeedbackRepository;
import nc.ukma.thor.spms.repository.UserRepository;
import nc.ukma.thor.spms.service.ProjectServiceImpl;

@Controller
public class HelloWorldController {
		
	
    @RequestMapping(value="/", method = RequestMethod.GET)
    public String sayHello(ModelMap model) {
        	
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
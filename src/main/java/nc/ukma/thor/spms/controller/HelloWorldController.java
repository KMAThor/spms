package nc.ukma.thor.spms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nc.ukma.thor.spms.repository.UserRepository;

@Controller
@RequestMapping("/")
public class HelloWorldController {
 
	@Autowired
	private UserRepository userRepository;
	
    @RequestMapping(method = RequestMethod.GET)
    public String sayHello(ModelMap model) {
        model.addAttribute("greeting", "Hello World!");
        model.addAttribute("numberOfUsers", userRepository.getNumberOfUsers());;
        
        return "index";
    }
  
}
package nc.ukma.thor.spms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/authentication")
public class AuthenticationController {
	
    @RequestMapping(method = RequestMethod.GET)
    public String sayHello(ModelMap model) {
        return "authentication";
    }
  
}
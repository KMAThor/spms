package nc.ukma.thor.spms.controller;

import nc.ukma.thor.spms.service.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class ProjectController {

    @Autowired
    public ProjectServiceImpl projectServiceImpl;
}

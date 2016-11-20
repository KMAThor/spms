package nc.ukma.thor.spms.controller;

import nc.ukma.thor.spms.dto.dataTable.DataTableRequestDTO;
import nc.ukma.thor.spms.dto.dataTable.DataTableResponseDTO;
import nc.ukma.thor.spms.dto.dataTable.ProjectTableDTO;
import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Trait;
import nc.ukma.thor.spms.entity.TraitCategory;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.repository.ProjectRepository;
import nc.ukma.thor.spms.service.ProjectService;
import nc.ukma.thor.spms.service.TeamService;
import nc.ukma.thor.spms.service.TraitCategoryService;
import nc.ukma.thor.spms.service.TraitService;
import nc.ukma.thor.spms.service.UserService;
import nc.ukma.thor.spms.util.DateUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/project/")
public class ProjectController {

    @Autowired
    private ProjectService projectService;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private TeamService teamService;
    @Autowired
    private UserService userService;
    @Autowired
    private TraitCategoryService traitCategoryService;
    @Autowired
    private TraitService traitService;

    @ResponseBody
	@RequestMapping(path = "/view/", method = RequestMethod.POST)
	public DataTableResponseDTO<ProjectTableDTO> viewProjects(HttpServletRequest req,
			@RequestBody DataTableRequestDTO dataTableRequest) {
		List<Project> projectsToShow = projectRepository.getProjects(
				dataTableRequest.getStart(),
				dataTableRequest.getLength(),
				dataTableRequest.getOrder().get(0).getColumn(),
				dataTableRequest.getOrder().get(0).getDir(),
				dataTableRequest.getSearch().getValue());

		Long numberOfUsers = projectRepository.count();
		Long numberOfUsersToShow = projectRepository.count(dataTableRequest.getSearch().getValue());
		DataTableResponseDTO<ProjectTableDTO> dataTableResponse = new DataTableResponseDTO<ProjectTableDTO>(
				dataTableRequest.getDraw(),
				numberOfUsers, numberOfUsersToShow,
				ProjectTableDTO.convertFrom(projectsToShow));
		return dataTableResponse;
	}

    @RequestMapping(path="/view/{id}/", method = RequestMethod.GET)
    public String viewProject(@PathVariable long id, Model model ){
    	Project project = projectService.getById(id);
    	User chiefMentor = project.getChiefMentor();
    	if(chiefMentor != null) project.setChiefMentor(userService.getUserById(chiefMentor.getId()));
    	model.addAttribute("project", project);
    	model.addAttribute("teams", teamService.getTeamsByProject(project));
    	model.addAttribute("traitCategories", traitCategoryService.getAllCategoriesWithTraits());
    	model.addAttribute("traitsAssociatedWithProject", traitService.getTraitsWithoutNamesByProject(project));
        /*List<User> mentors = userService.getMentors();
        model.addAttribute("mentors",  mentors);*/
        return "project";
    }

    @RequestMapping(path="/create/", method = RequestMethod.POST)
    public String createProject(HttpServletRequest request, Model model ){
    	Project project = new Project();
    	project.setName(request.getParameter("name"));
    	project.setDescription(request.getParameter("description"));
    	project.setStartDate(DateUtil.getTimeStamp(request.getParameter("startDate")));
    	project.setEndDate(DateUtil.getTimeStamp(request.getParameter("endDate")));
    	projectService.create(project);
    	return "redirect:/project/view/"+project.getId()+"/";
    }

    @RequestMapping(path="/update/{id}/", method = RequestMethod.POST)
    public String updateProject(@PathVariable long id, HttpServletRequest request, Model model ){
    	Project project = new Project(id);
    	project.setName(request.getParameter("name"));
    	project.setDescription(request.getParameter("description"));
    	project.setStartDate(DateUtil.getTimeStamp(request.getParameter("startDate")));
    	project.setEndDate(DateUtil.getTimeStamp(request.getParameter("endDate")));
    	projectService.update(project);
        return "redirect:/project/view/"+id+"/";
    }

    @RequestMapping(path="/delete/{id}/", method = RequestMethod.GET)
    public String deleteProject(@PathVariable long id){
    	Project project = new Project(id);
    	projectService.delete(project);
        return "redirect:/";
    }

    @ResponseBody
    @RequestMapping(path="/update/{projectId}/addTrait/{traitId}/", method = RequestMethod.GET)
    public String addTraitToProject(@PathVariable long projectId, @PathVariable long traitId){
    	projectRepository.addTraitToProject(new Trait(traitId), new Project(projectId));
        return "success";
    }

    @ResponseBody
    @RequestMapping(path="/update/{projectId}/deleteTrait/{traitId}/", method = RequestMethod.GET)
    public String deleteTraitFromProject(@PathVariable long projectId, @PathVariable long traitId){
    	projectRepository.deleteTraitFromProject(new Trait(traitId), new Project(projectId));
        return "success";
    }

    @ResponseBody
    @RequestMapping(path="/update/{projectId}/addTraitCategory/{traitCategoryId}/", method = RequestMethod.GET)
    public String addTraitCategoryToProject(@PathVariable long projectId, @PathVariable short traitCategoryId){
    	projectRepository.addTraitCategoryToProject(new TraitCategory(traitCategoryId), new Project(projectId));
        return "success";
    }

    @ResponseBody
    @RequestMapping(path="/update/{projectId}/deleteTraitCategory/{traitCategoryId}/", method = RequestMethod.GET)
    public String deleteTraitCategoryFromProject(@PathVariable long projectId, @PathVariable short traitCategoryId){
    	projectRepository.deleteTraitCategoryFromProject(new TraitCategory(traitCategoryId), new Project(projectId));
        return "success";
    }

}

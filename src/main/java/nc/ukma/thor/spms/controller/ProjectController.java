package nc.ukma.thor.spms.controller;

import nc.ukma.thor.spms.dto.dataTable.DataTableRequestDTO;
import nc.ukma.thor.spms.dto.dataTable.DataTableResponseDTO;
import nc.ukma.thor.spms.dto.dataTable.ProjectTableDTO;
import nc.ukma.thor.spms.entity.File;
import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.entity.Trait;
import nc.ukma.thor.spms.entity.TraitCategory;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.entity.report.ProjectReport;
import nc.ukma.thor.spms.repository.ProjectRepository;
import nc.ukma.thor.spms.service.FileService;
import nc.ukma.thor.spms.service.ProjectService;
import nc.ukma.thor.spms.service.TeamService;
import nc.ukma.thor.spms.service.TraitCategoryService;
import nc.ukma.thor.spms.service.TraitService;
import nc.ukma.thor.spms.service.UserService;
import nc.ukma.thor.spms.util.DateUtil;
import nc.ukma.thor.spms.util.FileBucket;
import nc.ukma.thor.spms.util.FileValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.io.IOException;
import java.security.Principal;
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
    @Autowired
	private CommonsMultipartResolver multipartResolver;
	@Autowired
	private String fileUploadLocation;
	@Autowired
	private FileValidator fileValidator;
	@Autowired
	private FileService fileService;

    @ResponseBody
	@RequestMapping(path = "/all/view/", method = RequestMethod.POST)
	public DataTableResponseDTO<ProjectTableDTO> viewProjects(HttpServletRequest req,
			@RequestBody DataTableRequestDTO dataTableRequest, Principal principal) {
    	
    	List<Project> projectsToShow;
    	Long numberOfProjectsToShow;
    	Long numberOfProjects;
    	User user = userService.getUser(principal.getName());
    	if(user.getRole() == Role.MENTOR){
    		projectsToShow = projectRepository.getProjectsByMentor(
    				dataTableRequest.getStart(),
    				dataTableRequest.getLength(),
    				dataTableRequest.getOrder().get(0).getColumn(),
    				dataTableRequest.getOrder().get(0).getDir(),
    				dataTableRequest.getSearch().getValue(),
    				user.getId());
    		numberOfProjects = projectRepository.countProjectsByMentor(user.getId());
    		numberOfProjectsToShow = projectRepository.countProjectsByMentor(dataTableRequest.getSearch().getValue(), user.getId());
    	}else{
    		projectsToShow = projectRepository.getProjects(
    				dataTableRequest.getStart(),
    				dataTableRequest.getLength(),
    				dataTableRequest.getOrder().get(0).getColumn(),
    				dataTableRequest.getOrder().get(0).getDir(),
    				dataTableRequest.getSearch().getValue());
    		numberOfProjects = projectRepository.count();
    		numberOfProjectsToShow = projectRepository.count(dataTableRequest.getSearch().getValue());
    	}
		
		DataTableResponseDTO<ProjectTableDTO> dataTableResponse = new DataTableResponseDTO<ProjectTableDTO>(
				dataTableRequest.getDraw(),
				numberOfProjects, numberOfProjectsToShow,
				ProjectTableDTO.convertFrom(projectsToShow));
		return dataTableResponse;
	}
    /*  @ResponseBody
	@RequestMapping(path = "/view/chiefMentor/{mentorId}/", method = RequestMethod.POST)
	public DataTableResponseDTO<ProjectTableDTO> viewProjectsOfMentor(HttpServletRequest req,
			@RequestBody DataTableRequestDTO dataTableRequest) {
		List<Project> projectsToShow = projectRepository.getActiveProjectsByMentor();

		Long numberOfUsers = projectRepository.count();
		Long numberOfUsersToShow = projectRepository.count(dataTableRequest.getSearch().getValue());
		DataTableResponseDTO<ProjectTableDTO> dataTableResponse = new DataTableResponseDTO<ProjectTableDTO>(
				dataTableRequest.getDraw(),
				numberOfUsers, numberOfUsersToShow,
				ProjectTableDTO.convertFrom(projectsToShow));
		return dataTableResponse;
	}*/

    @RequestMapping(path="/view/{id}/", method = RequestMethod.GET)
    public String viewProject(@PathVariable long id, Model model ){
    	Project project = projectService.getById(id);
    	User chiefMentor = project.getChiefMentor();
    	if(chiefMentor != null) project.setChiefMentor(userService.getUserById(chiefMentor.getId()));
    	model.addAttribute("project", project);
    	model.addAttribute("teams", teamService.getTeamsByProject(project));
    	model.addAttribute("traitCategories", traitCategoryService.getAllCategoriesWithTraits());
    	model.addAttribute("traitsAssociatedWithProject", traitService.getTraitsWithoutNamesByProject(project));
        return "project";
    }

    public ProjectReport viewProjectReport(@PathVariable long id, Model model ){
        return projectRepository.getProjectReport(id);
    }
    
    @RequestMapping(path="/create/", method = RequestMethod.POST)
    public String createProject(HttpServletRequest request, Model model ){
    	Project project = new Project();
    	project.setName(request.getParameter("name"));
    	project.setDescription(request.getParameter("description"));
    	project.setStartDate(DateUtil.getTimeStamp(request.getParameter("startDate")));
    	project.setEndDate(DateUtil.getTimeStamp(request.getParameter("endDate")));
    	String cheifMentorId = request.getParameter("cheifMentorId");
    	if(!cheifMentorId.isEmpty()) project.setChiefMentor(new User(Long.valueOf(cheifMentorId)));
    	projectService.create(project);
    	return "redirect:/project/view/"+project.getId()+"/";
    }


    @RequestMapping(path="/update/", method = RequestMethod.POST)
    public String updateProject(@RequestParam long id, HttpServletRequest request, Model model){
    	Project project = new Project(id);
    	project.setName(request.getParameter("name"));
    	project.setDescription(request.getParameter("description"));
    	project.setStartDate(DateUtil.getTimeStamp(request.getParameter("startDate")));
    	project.setEndDate(DateUtil.getTimeStamp(request.getParameter("endDate")));
    	if(request.getParameter("isCompleted") != null) project.setIsCompleted(true);
    	project.setChiefMentor(new User(Long.valueOf(request.getParameter("cheifMentorId"))));
    	projectService.update(project);
        return "redirect:/project/view/"+id+"/";
    }
     
    /*
    //sendMessageTeamAddedToProject
    @ResponseBody
    @RequestMapping(path="/project/{projectId}/addTeam/{teamId}", method = RequestMethod.POST)
    public String addTeamToProject(@PathVariable long projectId, @PathVariable long teamId, Model model){
    	Project project = projectService.getProject(projectId);
    	Team team = teamService.getById(teamId);
    	if (project.getTeams() == null) {
    		project.setTeams(new ArrayList<Team>());
    	}
    	project.getTeams().add(team);
    	projectService.update(project);
    	model.addAttribute("project", project);
    	if (team.getMembers() == null) {
    		team.setMembers(new HashMap<User, UserStatus>());
    	}
    	List<User> usersToNotify = new ArrayList<>(team.getMembers().keySet());
    	EmailSender.sendMessageTeamAddedToProject(usersToNotify);
    	return "project";
    }
    */
    
    @RequestMapping(path="/delete/", method = RequestMethod.POST)
    public String deleteProject(@RequestParam long id){
    	Project project = new Project(id);
    	projectService.delete(project);
        return "redirect:/";
    }
    
    @ResponseBody
    @RequestMapping(path="/addTrait/", method = RequestMethod.POST)
    public String addTraitToProject(@RequestParam long projectId, @RequestParam long traitId){
    	projectRepository.addTraitToProject(new Trait(traitId), new Project(projectId));
        return "success";
    }

    @ResponseBody
    @RequestMapping(path="/deleteTrait/", method = RequestMethod.POST)
    public String deleteTraitFromProject(@RequestParam long projectId, @RequestParam long traitId){
    	projectRepository.deleteTraitFromProject(new Trait(traitId), new Project(projectId));
        return "success";
    }

    @ResponseBody
    @RequestMapping(path="/addTraitCategory/", method = RequestMethod.POST)
    public String addTraitCategoryToProject(@RequestParam long projectId, @RequestParam short traitCategoryId){
    	projectRepository.addTraitCategoryToProject(new TraitCategory(traitCategoryId), new Project(projectId));
        return "success";
    }
    
    @ResponseBody
    @RequestMapping(path="/deleteTraitCategory/", method = RequestMethod.POST)
    public String deleteTraitCategoryFromProject(@RequestParam long projectId, @RequestParam short traitCategoryId){
    	projectRepository.deleteTraitCategoryFromProject(new TraitCategory(traitCategoryId), new Project(projectId));
        return "success";
    }
    
	@RequestMapping(path="/uploadFile/", method = RequestMethod.POST)
	public String singleFileUpload(@Validated FileBucket fileBucket, BindingResult result, @RequestParam long id) throws IOException {

		if (result.hasErrors()) {
			System.out.println("validation errors");
			return "redirect:/project/view/" + id +"/";
		} else {
			System.out.println("Fetching file");
			MultipartFile multipartFile = fileBucket.getFile();

			//Now do something with file...
			FileCopyUtils.copy(fileBucket.getFile().getBytes(), new java.io.File(fileUploadLocation + fileBucket.getFile().getOriginalFilename()));

			String fileName = multipartFile.getOriginalFilename();
			File file = new File(fileName, new Project(id));
			fileService.create(file);
			return "redirect:/project/view/"+id+"/";
		}
	}

}

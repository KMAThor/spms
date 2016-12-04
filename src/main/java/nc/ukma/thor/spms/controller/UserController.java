package nc.ukma.thor.spms.controller;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import nc.ukma.thor.spms.dto.dataTable.DataTableRequestDTO;
import nc.ukma.thor.spms.dto.dataTable.DataTableResponseDTO;
import nc.ukma.thor.spms.dto.dataTable.UserTableDTO;
import nc.ukma.thor.spms.entity.HrFeedback;
import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.repository.UserRepository;
import nc.ukma.thor.spms.service.HrFeedbackService;
import nc.ukma.thor.spms.service.ProjectService;
import nc.ukma.thor.spms.service.UserService;
import nc.ukma.thor.spms.service.impl.HrFeedbackServiceImpl;

@Controller
@RequestMapping("/user/")
public class UserController {
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private HrFeedbackService hrFeedbackService;
	
	@RequestMapping(path="/", method = RequestMethod.GET)
	public String usersPage(Model model){
        return "users";
    }
	
	@RequestMapping(path="/view/{id}/", method = RequestMethod.GET)
    public String viewProject(@PathVariable long id, Model model, Principal principal){
    	User user = userService.getUserById(id);
    	if(user == null) return "redirect:/404/";
    	model.addAttribute("user", user);
    	List<Project> projects = projectService.getProjectsByUser(id);
    	model.addAttribute("projects", projects);
        return "user";
    }
	
	@ResponseBody
	@RequestMapping(path = "/all/view/", method = RequestMethod.POST)
	public DataTableResponseDTO<UserTableDTO> viewUsers(HttpServletRequest req,
			@RequestBody DataTableRequestDTO dataTableRequest) {
		List<User> usersToShow = userRepository.getUsers(
				dataTableRequest.getStart(),
				dataTableRequest.getLength(),
				dataTableRequest.getOrder().get(0).getColumn(),
				dataTableRequest.getOrder().get(0).getDir(),
				dataTableRequest.getSearch().getValue());
        
		Long numberOfUsers = userRepository.count();
		Long numberOfUsersToShow = userRepository.countFiltered(dataTableRequest.getSearch().getValue());
		DataTableResponseDTO<UserTableDTO> dataTableResponse = new DataTableResponseDTO<UserTableDTO>(dataTableRequest.getDraw(),
				numberOfUsers, numberOfUsersToShow, UserTableDTO.convertFrom(usersToShow));
		//System.out.println(dataTableRequest);
		//System.out.println(dataTableResponse);
		return dataTableResponse;
	}
	
	@ResponseBody
	@RequestMapping(path = "/allWithRole/{userRole}/view/", method = RequestMethod.POST)
	public DataTableResponseDTO<UserTableDTO> viewUsersByRole(HttpServletRequest req, @PathVariable String userRole,
			@RequestBody DataTableRequestDTO dataTableRequest) {
		Role role = Role.valueOf(userRole.toUpperCase());
		List<User> usersToShow = userRepository.getUsersByRole(
				dataTableRequest.getStart(),
				dataTableRequest.getLength(),
				dataTableRequest.getOrder().get(0).getColumn(),
				dataTableRequest.getOrder().get(0).getDir(),
				dataTableRequest.getSearch().getValue(),
				role);
		Long numberOfUsers = userRepository.countUsersByRole(role);
		Long numberOfUsersToShow = userRepository.countUsersByRoleFiltered(role, dataTableRequest.getSearch().getValue());
		DataTableResponseDTO<UserTableDTO> dataTableResponse = new DataTableResponseDTO<UserTableDTO>(dataTableRequest.getDraw(),
				numberOfUsers, numberOfUsersToShow, UserTableDTO.convertFrom(usersToShow));
		return dataTableResponse;
	}
	
	@ResponseBody
	@RequestMapping(path = "/allFreeWithRole/{userRole}/view/", method = RequestMethod.POST)
	public DataTableResponseDTO<UserTableDTO> viewFreeUsersByRole(HttpServletRequest req, @PathVariable String userRole,
			@RequestBody DataTableRequestDTO dataTableRequest) {
		Role role = Role.valueOf(userRole.toUpperCase());
		List<User> usersToShow = userRepository.getFreeUsersByRole(
				dataTableRequest.getStart(),
				dataTableRequest.getLength(),
				dataTableRequest.getOrder().get(0).getColumn(),
				dataTableRequest.getOrder().get(0).getDir(),
				dataTableRequest.getSearch().getValue(),
				role);
		Long numberOfUsers = userRepository.countFreeUsersByRole(role);
		Long numberOfUsersToShow = userRepository.countFreeUsersByRoleFiltered(role, dataTableRequest.getSearch().getValue());
		DataTableResponseDTO<UserTableDTO> dataTableResponse = new DataTableResponseDTO<UserTableDTO>(dataTableRequest.getDraw(),
				numberOfUsers, numberOfUsersToShow, UserTableDTO.convertFrom(usersToShow));
		return dataTableResponse;
	}
	
	@ResponseBody
	@RequestMapping(path="/changeStatus/", method = RequestMethod.POST)
    public String changeStatus(@RequestParam long teamId, @RequestParam long userId, @RequestParam long newStatus, @RequestParam String newComment){
		userService.changeUserStatus(teamId, userId, newStatus, newComment);
    	return "success";
    }
	
	@RequestMapping(path="/report/{studentId}/{projectId}/", method = RequestMethod.GET)
    public void viewUserReport(@PathVariable long studentId, @PathVariable long projectId, HttpServletResponse response){
		User student = userService.getUserById(studentId);
		System.out.println(student);
    	Workbook wb = userService.getReportStudentActivityInProjectInXlsFormat(student, new Project(projectId));
    	response.setContentType("application/xls");
    	response.setHeader("Content-disposition", "attachment; filename=Report_about_"+student.getEmail()+".xls");
        try {
			wb.write(response.getOutputStream());
	        response.flushBuffer();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}

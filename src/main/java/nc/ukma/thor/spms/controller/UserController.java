package nc.ukma.thor.spms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
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
import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.repository.UserRepository;
import nc.ukma.thor.spms.service.UserService;

@Controller
@RequestMapping("/user/")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(path="/", method = RequestMethod.GET)
	public String usersPage(Model model){
        return "users";
    }
	
	@ResponseBody
	@RequestMapping(path = "/view/", method = RequestMethod.POST)
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
	@RequestMapping(path = "/view/{userRole}/", method = RequestMethod.POST)
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
	@RequestMapping(path = "/view/free/{userRole}/", method = RequestMethod.POST)
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
    public String changeStatus(@RequestParam long team_id, @RequestParam long user_id, @RequestParam long new_status, @RequestParam String new_comment){
		userService.changeUserStatus(team_id, user_id, new_status, new_comment);
    	return "success";
    }

}

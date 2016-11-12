package nc.ukma.thor.spms.controller;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nc.ukma.thor.spms.dto.DataTable.DataTableOrderDTO;
import nc.ukma.thor.spms.dto.DataTable.DataTableRequestDTO;
import nc.ukma.thor.spms.dto.DataTable.DataTableResponseDTO;
import nc.ukma.thor.spms.dto.DataTable.UserDTO;
import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.repository.UserRepository;

@Controller
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@RequestMapping(path="/users/", method = RequestMethod.GET)
	public String usersPage(Model model){
        return "users";
    }
	
	@ResponseBody
    @RequestMapping(path="/view/users/", method = RequestMethod.POST)
    public DataTableResponseDTO<UserDTO> viewUsers(HttpServletRequest req, @RequestBody DataTableRequestDTO dataTableRequest){
		List<User> usersToShow = userRepository.getUsers(dataTableRequest.getStart(), dataTableRequest.getLength());
		Long numberOfUsers = userRepository.count();
		DataTableResponseDTO<UserDTO> dataTableResponse = new DataTableResponseDTO<UserDTO>(
				dataTableRequest.getDraw(), numberOfUsers, numberOfUsers, UserDTO.convertFrom(usersToShow));
		System.out.println(dataTableRequest);
		System.out.println(dataTableResponse);
        return dataTableResponse;
    }

}

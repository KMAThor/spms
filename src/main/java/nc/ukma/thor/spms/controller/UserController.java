package nc.ukma.thor.spms.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import nc.ukma.thor.spms.dto.dataTable.DataTableRequestDTO;
import nc.ukma.thor.spms.dto.dataTable.DataTableResponseDTO;
import nc.ukma.thor.spms.dto.dataTable.UserTableDTO;
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
	@RequestMapping(path = "/view/users/", method = RequestMethod.POST)
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

}

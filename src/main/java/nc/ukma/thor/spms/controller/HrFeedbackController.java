package nc.ukma.thor.spms.controller;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import nc.ukma.thor.spms.entity.HrFeedback;
import nc.ukma.thor.spms.entity.MeetingFeedback;
import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.SpmsUserDetails;
import nc.ukma.thor.spms.entity.TraitFeedback;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.service.HrFeedbackService;
import nc.ukma.thor.spms.service.UserService;
import nc.ukma.thor.spms.util.DateUtil;

@Controller
@RequestMapping("/hrFeedback/")
public class HrFeedbackController {
	
	@Autowired
	private HrFeedbackService hrFeedbackService;
	
	@Autowired
	private UserService userService;
	
	@ResponseBody
    @RequestMapping(path="/get/by_student/{studentId}", method = RequestMethod.GET)
    public String createMeeting(@PathVariable Long studentId){
		User student = new User();
		student.setId(studentId);
		List<HrFeedback> hrFeedbacks = hrFeedbackService.getHrFeedbacksByStudent(student);
		return Arrays.toString(hrFeedbacks.toArray());
	}
	
	

	@RequestMapping(path= "/create/{studentId}/", method = RequestMethod.GET)
	public String getCreateMeetingFeedbackForm(Model model, @PathVariable long studentId){
		User student = userService.getUserById(studentId);
		model.addAttribute("student", student);
		
		return "createHrFeedback";
	}
	
	@RequestMapping(path= "/create/{studentId}/", method = RequestMethod.POST)
	public String createHrFeedbackForm(Model model,
			@PathVariable Long studentId,
			@RequestParam String topic,
			@RequestParam String summary,
			@RequestParam(required=false) Long authorId,
			Authentication authentication) {
		User user = new User(((SpmsUserDetails) authentication.getPrincipal()).getId());
		User author = authorId == null ? user : new  User(authorId);
		User student = new User(studentId);
		HrFeedback hrFeedback = new HrFeedback(topic, summary, student, user, author);
		
		hrFeedbackService.create(hrFeedback);
		
		return "redirect:/user/view/"+studentId+"/";
	}

	
}


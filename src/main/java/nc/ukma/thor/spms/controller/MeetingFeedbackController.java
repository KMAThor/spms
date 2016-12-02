package nc.ukma.thor.spms.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import nc.ukma.thor.spms.entity.MeetingFeedback;
import nc.ukma.thor.spms.entity.TraitFeedback;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.repository.MeetingRepository;
import nc.ukma.thor.spms.service.MeetingFeedbackService;
import nc.ukma.thor.spms.service.TraitCategoryService;
import nc.ukma.thor.spms.service.UserService;

@Controller
@RequestMapping("/meetingFeedback/")
public class MeetingFeedbackController {
	
	@Autowired
	private TraitCategoryService traitCategoryService;
	
	@Autowired
	private MeetingFeedbackService meetingFeedbackService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private MeetingRepository meetingRepository;
	
	
	@RequestMapping(path= "/view/{id}/", method = RequestMethod.GET)
	public String viewMeetingFeedbackForm(Model model, @PathVariable long id){
		MeetingFeedback meetingFeedback = meetingFeedbackService.getById(id);
		model.addAttribute("traitCategories", traitCategoryService.getAllCategoriesWithTraitsByMeeting(meetingFeedback.getMeeting()));
		model.addAttribute("meetingFeedback", meetingFeedback);
		return "viewMeetingFeedback";
	}
	
	@RequestMapping(path= "/create/{studentId}/{meetingId}/", method = RequestMethod.GET)
	public String getCreateMeetingFeedbackForm(Model model, @PathVariable long studentId, @PathVariable long meetingId){
		model.addAttribute("traitCategories", traitCategoryService.getAllCategoriesWithTraitsByMeeting(meetingId));
		model.addAttribute("student", userService.getUserById(studentId));
		model.addAttribute("meeting", meetingRepository.getById(meetingId));
		return "createMeetingFeedback";
	}
	
	@RequestMapping(path= "/create/", method = RequestMethod.POST)
	public String createMeetingFeedbackForm(Model model, HttpServletRequest request,
			@RequestParam long studentId,
			@RequestParam long meetingId,
			@RequestParam List<Long> traitIds,
			@RequestParam List<Short> scores,
			@RequestParam List<String> comments,
			@RequestParam String summary,
			Principal principal){
		
		User user = userService.getUser(principal.getName());
		MeetingFeedback meetingFeedback = new MeetingFeedback(summary, studentId, meetingId, user);
		for(int i = 0; i < traitIds.size(); i++){
			meetingFeedback.addTraitFeedback(new TraitFeedback(scores.get(i),comments.get(i),traitIds.get(i)));
		}
		meetingFeedbackService.create(meetingFeedback);
		
		return "redirect:/meeting/view/"+meetingId+"/";
	}
	
	@RequestMapping(path= "/edit/{meetingFeedbackId}/", method = RequestMethod.GET)
	public String editMeetingFeedbackForm(Model model, @PathVariable long meetingFeedbackId){
		MeetingFeedback meetingFeedback = meetingFeedbackService.getById(meetingFeedbackId);
		model.addAttribute("traitCategories", traitCategoryService.getAllCategoriesWithTraitsByMeeting(meetingFeedback.getMeeting()));
		model.addAttribute("meetingFeedback", meetingFeedback);
		return "editMeetingFeedback";
	}
	
	@RequestMapping(path= "/update/", method = RequestMethod.POST)
	public String updateMeetingFeedback(Model model, HttpServletRequest request,
			@RequestParam long id,
			@RequestParam List<Long> traitFeedbackIds,
			@RequestParam List<Long> traitIds,
			@RequestParam List<Short> scores,
			@RequestParam List<String> comments,
			@RequestParam String summary,
			@RequestParam long studentId, 
			@RequestParam long meetingId,
			Principal principal){
		
		User user = userService.getUser(principal.getName());
		MeetingFeedback meetingFeedback = new MeetingFeedback(id, summary, studentId, meetingId, user);
		for(int i = 0; i < traitIds.size(); i++){
			meetingFeedback.addTraitFeedback(new TraitFeedback(traitFeedbackIds.get(i), scores.get(i),comments.get(i),traitIds.get(i)));
		}
		meetingFeedbackService.update(meetingFeedback);
		
		return "redirect:/meeting/view/"+meetingId+"/";
	}
	
	@RequestMapping(path="/delete/", method = RequestMethod.POST)
    public String deleteMeetingFeedback(@RequestParam long id){
    	meetingFeedbackService.delete(new MeetingFeedback(id));
        return "redirect:/";
    }
}

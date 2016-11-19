package nc.ukma.thor.spms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nc.ukma.thor.spms.entity.TraitCategory;
import nc.ukma.thor.spms.repository.TraitCategoryRepository;

@Controller
@RequestMapping("/meetingFeedback/")
public class MeetingFeedbackController {
	
	@Autowired
	private TraitCategoryRepository traitCategoryRepository;
	
	@RequestMapping(path= "/create/", method = RequestMethod.GET)
	public String getCreateMeetingFeedbackForm(Model model){
		List<TraitCategory> traitCategories = traitCategoryRepository.getAllCategoriesWithTraitsByProject((long) 4);
		System.out.println(traitCategories);
		model.addAttribute("traitCategories",traitCategories);
		return "createMeetingFeedback";
	}
}

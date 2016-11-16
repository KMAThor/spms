package nc.ukma.thor.spms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nc.ukma.thor.spms.entity.TraitCategory;
import nc.ukma.thor.spms.repository.TraitCategoryRepository;

@Controller
public class TraitFeedbackController {
	
	@Autowired
	private TraitCategoryRepository traitCategoryRepository;
	
	
	@RequestMapping(path="/traitFeedback/create/", method = RequestMethod.GET)
	public String createTraitFeedback(ModelMap model){
		//just for test
		List<TraitCategory> traitCategories = traitCategoryRepository.getAllCategoriesWithTraitsByProject((long) 4);
		System.out.println(traitCategories);
		model.addAttribute("traitCategories",traitCategories);
		return "createTraitFeedback";
	}
}

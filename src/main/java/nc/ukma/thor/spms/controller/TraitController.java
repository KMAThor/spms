package nc.ukma.thor.spms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import nc.ukma.thor.spms.entity.Trait;
import nc.ukma.thor.spms.entity.TraitCategory;
import nc.ukma.thor.spms.service.TraitCategoryService;
import nc.ukma.thor.spms.service.TraitService;

@Controller
public class TraitController {
	
	@Autowired
	private TraitCategoryService traitCategoryService;
	@Autowired
	private TraitService traitService;
	
	/*
	 * Displays trait management page
	 * */
	@RequestMapping(path="/traitManager/", method = RequestMethod.GET)
	public String traitManager(ModelMap model){
		model.addAttribute("traitCategories", traitCategoryService.getAllCategoriesWithTraits());
		return "traitManager";
	}
	
	@ResponseBody
	@RequestMapping(path="/traitCategory/create/", method = RequestMethod.POST)
	public TraitCategory createTraitCategory(@RequestParam String name){
		TraitCategory newTraitCategory = new TraitCategory(name);
		traitCategoryService.create(newTraitCategory);
		return newTraitCategory;
	}
	
	@ResponseBody
	@RequestMapping(path="/traitCategory/update/", method = RequestMethod.POST)
	public String updateTraitCategory(@RequestParam short id, @RequestParam String name){
		TraitCategory traitCategory = new TraitCategory(id, name);
		traitCategoryService.update(traitCategory);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(path="/traitCategory/delete/", method = RequestMethod.POST)
	public String deleteTraitCategory(@RequestParam long id){
		traitCategoryService.delete(new TraitCategory((short) id));
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(path="/trait/create/", method = RequestMethod.POST)
	public Trait createTrait(@RequestParam String name, @RequestParam short categoryId){
		Trait newTrait = new Trait(name, new TraitCategory(categoryId));
		System.out.println(newTrait);
		traitService.create(newTrait);
		return newTrait;
	}
	
	@ResponseBody
	@RequestMapping(path="/trait/update/", method = RequestMethod.POST)
	public String updateTrait(@RequestParam long id, @RequestParam String name, @RequestParam short categoryId){
		Trait trait = new Trait(id, name, new TraitCategory(categoryId));
		traitService.update(trait);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping(path="/trait/delete/", method = RequestMethod.POST)
	public String deleteTrait(@RequestParam long id){
		traitService.delete(new Trait(id));
		return "success";
	}
}

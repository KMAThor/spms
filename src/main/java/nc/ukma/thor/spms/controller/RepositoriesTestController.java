package nc.ukma.thor.spms.controller;

import java.sql.Timestamp;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.Project;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.Trait;
import nc.ukma.thor.spms.entity.TraitCategory;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.repository.MeetingRepository;
import nc.ukma.thor.spms.repository.ProjectRepository;
import nc.ukma.thor.spms.repository.TeamRepository;
import nc.ukma.thor.spms.repository.TraitCategoryRepository;
import nc.ukma.thor.spms.repository.TraitRepository;
import nc.ukma.thor.spms.repository.UserRepository;
import nc.ukma.thor.spms.service.ProjectServiceImpl;

@Controller
@RequestMapping("test/repository/")
public class RepositoriesTestController {
	
	@Autowired
	private ProjectServiceImpl projectService;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private TeamRepository teamRepository;
	
	@Autowired
	private MeetingRepository meetingRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TraitRepository traitRepository;
	
	@Autowired
	private TraitCategoryRepository traitCategoryRepository;
	
	@RequestMapping(value="project", method = RequestMethod.GET)
    public String testProjectRepository(ModelMap model) {
		
		Timestamp t = new Timestamp(0);
		Project p = new Project("Super project3", "Some description3", t, t, false, null);
    	
		projectService.create(p);
		
		Project p2 =  projectService.getById(p.getId());
    	System.out.println("After creation:" + p);
    	
    	p2.setName(p2.getName()+"_UPDATED");
    	projectService.update(p2);
    	
    	p2 = projectService.getById(p2.getId());
    	System.out.println("After update:" + p2);
    	
    	projectService.delete(p2);
    	p2 = projectService.getById(p2.getId());
    	System.out.println("After delete:" + p2);
    	
    	System.out.println("get all active projects:" + projectRepository.getAllActiveProjects());
    	System.out.println("get all projects:" + projectRepository.getAllProjects());
        return "authentication";
    }
	
	@RequestMapping(value="team", method = RequestMethod.GET)
    public String testTeamRepository(ModelMap model) {
		Project project = new Project(4);
		Team team = new Team("New life", project);
		
		teamRepository.add(team);
		System.out.println("Get just created team:" + teamRepository.getById(team.getId()));
		
		team.setName(team.getName() + "_UPDATED");
		teamRepository.update(team);
		System.out.println("Get just updated team:" + teamRepository.getById(team.getId()));
		
		teamRepository.delete(team);
		System.out.println("Get just deleted team:" + teamRepository.getById(team.getId()));
		
		System.out.println("Get teams by user:" + teamRepository.getTeamsByUser(new User(2)));
		System.out.println("Get teams by project:" + teamRepository.getTeamsByProject(project));
		System.out.println("Get teams by project:" + teamRepository.getTeamsByProject(new Project(0)));
		
        return "authentication";
    }
	
	@RequestMapping(value="meeting", method = RequestMethod.GET)
    public String testMeetingRepository(ModelMap model) {
		Meeting meeting = new Meeting("Architecture planing", new Timestamp(2141324), new Team(5));
		
		meetingRepository.add(meeting);
		System.out.println("Get just created meeting:" + meetingRepository.getById(meeting.getId()));
		
		meeting.setTopic(meeting.getTopic()+"_UPDATED");
		meetingRepository.update(meeting);
		System.out.println("Get just updated meeting:" + meetingRepository.getById(meeting.getId()));
		
		meetingRepository.delete(meeting);
		System.out.println("Get just deleted meeting:" + meetingRepository.getById(meeting.getId()));
		
		Meeting meeting2 = new Meeting(6);
		System.out.println("Get presence for meeting with id 6:" + meetingRepository.getUsersPresentAtMeeting(meeting2));
		
		System.out.println("Mark user with id 5 as presente at meeting with id 6");
		meetingRepository.addUserToMeeting(new User(5), meeting2);
		System.out.println("Get presence for meeting with id 6:" + meetingRepository.getUsersPresentAtMeeting(meeting2));
		
		System.out.println("Mark user with id 5 as absent at meeting with id 6");
		meetingRepository.deleteUserFromMeeting(new User(5), meeting2);
		System.out.println("Get presence for meeting with id 6:" + meetingRepository.getUsersPresentAtMeeting(meeting2));
		
		System.out.println("Get meeting of team with id 5:" + meetingRepository.getMeetingsByTeam(new Team(5)));
        return "authentication";
    }
	
	@RequestMapping(value="user", method = RequestMethod.GET)
    public String testUserRepository(ModelMap model) {
		System.out.println("Get user with id = 0: " + userRepository.getUserById(0));
		System.out.println("Get user with email = hr@hr.com: " + userRepository.getUserByEmail("hr@hr.com"));
		
		Team team = new Team();
		team.setId(5);
		System.out.println("Get users from team with id = 5 " + userRepository.getUsersByTeam(team));
		
		Meeting meeting = new Meeting();
		meeting.setId(6);
		System.out.println("Get users present at meeting with id = 6 " + userRepository.getUsersPresentAtMeeting(meeting));
		
		return "authentication";
	}
	
	@RequestMapping(value="trait", method = RequestMethod.GET)
	public String testTraitRepository(ModelMap model) {
		TraitCategory traitCategory = new TraitCategory((short) 8);
		Trait trait = new Trait("Client manipulation skills", traitCategory);
		traitRepository.add(trait);
		Trait trait2 = traitRepository.getById(trait.getId());
		System.out.println("Get just created trait:" + trait2);
		
		trait2.setName(trait2.getName()+"_CHANGED");
		traitRepository.update(trait2);
		System.out.println("Get just updated trait:" + traitRepository.getById(trait2.getId()));
		
		System.out.println("Get all trait of category with id 9:" + traitRepository.getTraitsByTraitCategory(traitCategory));
		
		traitRepository.delete(trait2);
		System.out.println("Get just deleted trait:" + traitRepository.getById(trait2.getId()));
		return "authentication";
	}
	
	@RequestMapping(value="traitCategory", method = RequestMethod.GET)
	public String testTraiCategorytRepository(ModelMap model) {
		TraitCategory traitCategory = new TraitCategory("Some category");
		traitCategoryRepository.add(traitCategory);
		System.out.println(traitCategory);
		System.out.println("Get just added category: " + traitCategoryRepository.getById(traitCategory.getId()));
		
		traitCategory.setName(traitCategory.getName()+"_UPDATED");
		traitCategoryRepository.update(traitCategory);
		System.out.println("Get just updated category: " + traitCategoryRepository.getById(traitCategory.getId()));
		
		traitCategoryRepository.delete(traitCategory);
		System.out.println("Get just deleted category: " + traitCategoryRepository.getById(traitCategory.getId()));
		
		Trait trait = new Trait(21);
		
		System.out.println("Get category by trait: " + traitCategoryRepository.getCategoryByTrait(trait));
		
		System.out.println("Get all categories with traits: " 
				+ traitCategoryRepository.getAllCategoriesWithTraits());
		
		return "authentication";
	}
}

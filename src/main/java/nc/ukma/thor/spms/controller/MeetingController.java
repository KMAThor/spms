package nc.ukma.thor.spms.controller;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.MeetingFeedback;
import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.entity.SpmsUserDetails;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.entity.UserStatus;
import nc.ukma.thor.spms.service.MeetingService;
import nc.ukma.thor.spms.service.SpmsWebSecurityService;
import nc.ukma.thor.spms.service.UserService;
import nc.ukma.thor.spms.util.DateUtil;
import nc.ukma.thor.spms.mail.EmailSender;
import nc.ukma.thor.spms.repository.MeetingFeedbackRepository;

@Controller
@RequestMapping("/meeting/")
public class MeetingController {
	
    @Autowired
    private MeetingService meetingService;
    @Autowired
    private UserService userService;
    @Autowired
    private MeetingFeedbackRepository meetingFeedbackRepository;
    
    @Autowired
    private SpmsWebSecurityService spmsWebSecurityService;

    @ResponseBody
    @RequestMapping(path="/create/", method = RequestMethod.POST)
    public Long createMeeting(@RequestParam long teamId, @RequestParam String topic, @RequestParam String startDate){
    	Meeting meeting = new Meeting();
    	meeting.setTopic(topic);
    	meeting.setStartDate(DateUtil.getTimeStamp(startDate));
    	Team team = new Team(teamId);
    	meeting.setTeam(team);
    	meetingService.create(meeting);
    	
    	String topicName = "Meeting topic name: " + topic;
    	List<User> usersToNotify = new ArrayList<>(userService.getUsersByTeam(team));
		EmailSender.sendScheduleChangesMassage(usersToNotify, meeting.getStartDate().toString(), topicName);

        return meeting.getId();
    }
    
    @ResponseBody
    @RequestMapping(path="/createSeveral/", method = RequestMethod.POST)
    public Long createMeetings(@RequestParam long teamId, @RequestParam String topic, @RequestParam String date, @RequestParam String endDate){
    	Team team = new Team(teamId);
    	Timestamp ts = DateUtil.getTimeStamp(date);
    	Timestamp templateTs = ts;
    	Timestamp tsEnd = DateUtil.getTimeStamp(endDate);
    	
    	Long idFirstMeeting = null;
    	
    	do {
    		
        	Meeting meeting = new Meeting();
        	meeting.setTopic(topic);
        	meeting.setStartDate(ts);
        	meeting.setTeam(team);
        	meetingService.create(meeting);

        	Calendar cal = Calendar.getInstance();
        	cal.setTime(ts);
        	cal.add(Calendar.DATE, 7);
        	ts.setTime(cal.getTime().getTime());
        	
        	
        	if (idFirstMeeting == null) {
        		idFirstMeeting = meeting.getId();
        	}
        	
        	topic = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss").format(ts);
        	
    	} while (ts.before(tsEnd));
    	
    	String textWithEndDate = "Meeteing occur every week at the same day untill the end of the project on " + tsEnd.toString();
    	List<User> usersToNotify = new ArrayList<>(userService.getUsersByTeam(team));
		EmailSender.sendScheduleChangesMassage(usersToNotify, templateTs.toString(), textWithEndDate);	
		
        return idFirstMeeting;
    }
    
    @ResponseBody
    @RequestMapping(path="/update/", method = RequestMethod.POST)
    public String updateProject(@RequestParam long id, @RequestParam String topic, @RequestParam String startDate){
    	Meeting meeting = new Meeting(id);
    	meeting.setTopic(topic);
    	meeting.setStartDate(DateUtil.getTimeStamp(startDate));
    	meetingService.update(meeting);
    	return "success";
    }
    
    @ResponseBody
    @RequestMapping(path="/delete/", method = RequestMethod.POST)
    public String deleteMeeting(@RequestParam long id){
    	Meeting meeting = new Meeting(id);
    	meetingService.delete(meeting);
    	return "success";
    }
    
    @ResponseBody
    @RequestMapping(path="/addParticipant/", method = RequestMethod.POST)
    public String addParticipant(@RequestParam long userId, @RequestParam long meetingId){
    	meetingService.addUserToMeeting(userId, meetingId);
    	return "success";
    }
    
    @ResponseBody
    @RequestMapping(path="/deleteParticipant/", method = RequestMethod.POST)
    public String deleteParticipant(@RequestParam long userId, @RequestParam long meetingId){
    	meetingService.deleteUserFromMeeting(userId, meetingId);
    	return "success";
    }
	
	@RequestMapping(path="/view/{id}/", method = RequestMethod.GET)
    public String viewMeeting(@PathVariable long id, Model model, Authentication authentication){
    	Meeting meeting = meetingService.getWithParticipantsById(id);
    	SpmsUserDetails spmsUserDetails = (SpmsUserDetails) authentication.getPrincipal();
    	
    	User author = userService.getUserById(spmsUserDetails.getId());
    	model.addAttribute("meeting", meeting);
    	HashMap<User, UserStatus> members = userService.getStudentsByTeam(meeting.getTeam());
    	model.addAttribute("members", members);
    	
    	boolean isProjectCompleted = meetingService.isProjectCompleted(id);
    	model.addAttribute("isProjectCompleted", isProjectCompleted);
    	
    	List<MeetingFeedback> feedbacks = new ArrayList<MeetingFeedback>();
    	for(User member: members.keySet()){
    		MeetingFeedback meetingFeedback = meetingFeedbackRepository.getMeetingFeedbacksWithoutTraitsByMeetingStudentAuthor(meeting, member, author);
    		feedbacks.add(meetingFeedback);
    	}
    	// for admin, chief mentor and hr we need to provide ability to view all feedbacks of student
    	if(author.getRole()==Role.ADMIN || author.getRole()==Role.HR || spmsWebSecurityService.isUserChiefMentorOfProjectWithMeeting(spmsUserDetails, meeting.getId())){
    		List<List<MeetingFeedback>> mentorFeedbacksOnStudents = new ArrayList<List<MeetingFeedback>>();
    		List<MeetingFeedback> meetingFeedbacks;
    		for(User member: members.keySet()){
    			meetingFeedbacks = meetingFeedbackRepository.getMeetingFeedbacksWithoutTraitsByMeetingAndStudent(meeting, member);
    			// temp solution, better to do it in one query to database
    			meetingFeedbacks.removeIf(meetingFeedback -> {
    				return meetingFeedback.getAuthor().getId()==author.getId();
    			});
    			for(MeetingFeedback meetingFeedback: meetingFeedbacks){
    				meetingFeedback.setAuthor(userService.getUserById(meetingFeedback.getAuthor().getId()));
    			}
    			mentorFeedbacksOnStudents.add(meetingFeedbacks);
    		}
    		model.addAttribute("mentorFeedbacksOnStudents", mentorFeedbacksOnStudents);
    	}
    	model.addAttribute("feedbacks", feedbacks);
        return "meeting";
    }
	
}

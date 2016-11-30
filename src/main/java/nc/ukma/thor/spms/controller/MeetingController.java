package nc.ukma.thor.spms.controller;

import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.MeetingFeedback;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.entity.UserStatus;
import nc.ukma.thor.spms.service.MeetingService;
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

    @ResponseBody
    @RequestMapping(path="/create/", method = RequestMethod.POST)
    public Long createMeeting(@RequestParam long team_id, @RequestParam String topic, @RequestParam String start_date){
    	Meeting meeting = new Meeting();
    	meeting.setTopic(topic);
    	meeting.setStartDate(DateUtil.getTimeStamp(start_date));
    	Team team = new Team(team_id);
    	meeting.setTeam(team);
    	meetingService.create(meeting);
    	
    	List<User> usersToNotify = new ArrayList<>(userService.getUsersByTeam(team));
		EmailSender.sendScheduleChangesMassage(usersToNotify, meeting.getStartDate().toString());	

        return meeting.getId();
    }
    
    @ResponseBody
    @RequestMapping(path="/createSeveral/", method = RequestMethod.POST)
    public Long createMeetings(@RequestParam long team_id, @RequestParam String topic, @RequestParam String date, @RequestParam String end_date){
    	Team team = new Team(team_id);
    	Timestamp ts = DateUtil.getTimeStamp(date);
    	Timestamp tsend = DateUtil.getTimeStamp(end_date);
    	
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
        	
    	} while (ts.before(tsend));
    	
        return idFirstMeeting;
    }
    
    @ResponseBody
    @RequestMapping(path="/update/", method = RequestMethod.POST)
    public String updateProject(@RequestParam long id, @RequestParam String topic, @RequestParam String start_date){
    	Meeting meeting = new Meeting(id);
    	meeting.setTopic(topic);
    	meeting.setStartDate(DateUtil.getTimeStamp(start_date));
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
    public String addParticipant(@RequestParam long user_id, @RequestParam long meeting_id){
    	meetingService.addUserToMeeting(user_id, meeting_id);
    	return "success";
    }
    
    @ResponseBody
    @RequestMapping(path="/deleteParticipant/", method = RequestMethod.POST)
    public String deleteParticipant(@RequestParam long user_id, @RequestParam long meeting_id){
    	meetingService.deleteUserFromMeeting(user_id, meeting_id);
    	return "success";
    }
	
	@RequestMapping(path="/view/{id}/", method = RequestMethod.GET)
    public String viewMeeting(@PathVariable long id, Model model, Principal authUser){
    	Meeting meeting = meetingService.getWithParticipantsById(id);
    	User author = userService.getUser(authUser.getName());
    	model.addAttribute("meeting", meeting);
    	HashMap<User, UserStatus> members = userService.getStudentsByTeam(meeting.getTeam());
    	model.addAttribute("members", members);
    	List<MeetingFeedback> feedbacks = new ArrayList<MeetingFeedback>();
    	for(User member: members.keySet()){
    		MeetingFeedback meetingFeedback = meetingFeedbackRepository.getMeetingFeedbacksByMeetingStudentMentor(meeting, member, author);
    		feedbacks.add(meetingFeedback);
    	}
    	
    	model.addAttribute("feedbacks", feedbacks);
        return "meeting";
    }
	
}

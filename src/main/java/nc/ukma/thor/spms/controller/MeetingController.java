package nc.ukma.thor.spms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.service.MeetingService;
import nc.ukma.thor.spms.service.UserService;
import nc.ukma.thor.spms.util.DateUtil;

@Controller
public class MeetingController {
	
    @Autowired
    private MeetingService meetingService;
    @Autowired
    private UserService userService;

    @ResponseBody
    @RequestMapping(path="/meeting/create/", method = RequestMethod.POST)
    public String createMeeting(@RequestParam long team_id, @RequestParam String topic, @RequestParam String start_date){
    	Meeting meeting = new Meeting();
    	meeting.setTopic(topic);
    	meeting.setStartDate(DateUtil.getTimeStamp(start_date));
    	Team team = new Team(team_id);
    	meeting.setTeam(team);
    	meetingService.create(meeting);
        return "/spms/meeting/view/" + meeting.getId() + "/";
    }
    
    @ResponseBody
    @RequestMapping(path="/meeting/update/", method = RequestMethod.POST)
    public String updateProject(@RequestParam long id, @RequestParam String topic, @RequestParam String start_date){
    	Meeting meeting = new Meeting(id);
    	meeting.setTopic(topic);
    	meeting.setStartDate(DateUtil.getTimeStamp(start_date));
    	meetingService.update(meeting);
    	return "success";
    }
    
    @ResponseBody
    @RequestMapping(path="/meeting/delete/", method = RequestMethod.POST)
    public String deleteMeeting(@RequestParam long id){
    	Meeting meeting = new Meeting(id);
    	meetingService.delete(meeting);
    	return "success";
    }
	
	@RequestMapping(path="/meeting/view/{id}/", method = RequestMethod.GET)
    public String viewMeeting(@PathVariable long id, Model model ){
    	Meeting meeting = meetingService.getById(id);
    	model.addAttribute("meeting", meeting);
    	model.addAttribute("members", userService.getUsersByMeeting(meeting));
        return "meeting";
    }
	
}

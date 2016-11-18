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
import nc.ukma.thor.spms.util.DateUtil;

@Controller
public class MeetingController {
	
    @Autowired
    private MeetingService meetingService;

    @ResponseBody
    @RequestMapping(path="/create/meeting/", method = RequestMethod.POST)
    public String createMeeting(@RequestParam long team_id, @RequestParam String topic, @RequestParam String start_date){
    	Meeting meeting = new Meeting();
    	meeting.setTopic(topic);
    	meeting.setStartDate(DateUtil.getTimeStamp(start_date));
    	Team team = new Team(team_id);
    	meeting.setTeam(team);
    	meetingService.create(meeting);
        return "/spms/view/meeting/" + meeting.getId() + "/";
    }
    
    @ResponseBody
    @RequestMapping(path="/delete/meeting/", method = RequestMethod.POST)
    public String deleteMeeting(@RequestParam long id){
    	Meeting meeting = new Meeting(id);
    	meetingService.delete(meeting);
    	return "success";
    }
	
	@RequestMapping(path="/view/meeting/{id}/", method = RequestMethod.GET)
    public String viewMeeting(@PathVariable long id, Model model ){
    	Meeting meeting = meetingService.getById(id);
    	model.addAttribute("meeting", meeting);
        return "meeting";
    }
	
}

package nc.ukma.thor.spms.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nc.ukma.thor.spms.entity.Meeting;
import nc.ukma.thor.spms.entity.Team;
import nc.ukma.thor.spms.service.MeetingService;
import nc.ukma.thor.spms.service.TeamService;
import nc.ukma.thor.spms.util.DateUtil;

@Controller
public class MeetingController {
	
	@Autowired
    private TeamService teamService;
    @Autowired
    private MeetingService meetingService;

    @RequestMapping(path="/{team_id}/create/meeting/", method = RequestMethod.POST)
    public String createMeeting(@PathVariable long team_id, HttpServletRequest request, Model model){
    	Team team = teamService.getById(team_id);
    	Meeting meeting = new Meeting();
    	meeting.setTopic(request.getParameter("topic"));
    	meeting.setStartDate(DateUtil.getTimeStamp(request.getParameter("startDate")));
    	meeting.setTeam(team);
    	meetingService.create(meeting);
    	model.addAttribute("meeting", meeting);
        return "meeting";
    }
    
    @RequestMapping(path="/{meeting_id}/delete/meeting", method = RequestMethod.GET)
    public String deleteMeeting(@PathVariable long meeting_id){
    	Meeting meeting = meetingService.getById(meeting_id);
    	long team_id = meeting.getTeam().getId();
    	meetingService.delete(meeting);
    	return "redirect:/view/team/" + team_id + "/";
    }
	
	@RequestMapping(path="/view/meeting/{id}/", method = RequestMethod.GET)
    public String viewMeeting(@PathVariable long id, Model model ){
    	Meeting meeting = meetingService.getById(id);
    	model.addAttribute("meeting", meeting);
        return "meeting";
    }
	
}

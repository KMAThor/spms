package nc.ukma.thor.spms.controller;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import nc.ukma.thor.spms.entity.HrFeedback;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.service.HrFeedbackService;

@Controller
@RequestMapping("/hr_feedback/")
public class HrFeedbackController {
	
	@Autowired
	private HrFeedbackService HrFeedbackService;
	
	@ResponseBody
    @RequestMapping(path="/get/by_student/{studentId}", method = RequestMethod.GET)
    public String createMeeting(@PathVariable Long studentId){
		User student = new User();
		student.setId(studentId);
		List<HrFeedback> hrFeedbacks = HrFeedbackService.getHrFeedbacksByStudent(student);
		return Arrays.toString(hrFeedbacks.toArray());
	}

	
}


package nc.ukma.spms;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import nc.ukma.thor.spms.config.AppConfig;
import nc.ukma.thor.spms.config.SecurityConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {AppConfig.class,
		 SecurityConfig.class})
public class SecurityTest {
	
	@Autowired
	private WebApplicationContext context;
	
	private MockMvc mvc;

	@Before
	public void setup() {
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
				.apply(springSecurity())
				.build();
	}
	/*
	 * The goal of this test is simply involve every SpEL expression in SecurityConfig
	 * in order to check just syntax correctness, not logical
	 * */
	@Test
	@WithUserDetails(value="mentor@mentor.com", userDetailsServiceBeanName="userDetailsServiceImpl")
	public void testSecurityWithMentor() throws Exception {
		//Project
		mvc.perform(get("/traitManager/")).andExpect(status().isForbidden());
		mvc.perform(get("/trait/")).andExpect(status().isForbidden());
		mvc.perform(get("/traitCategory/")).andExpect(status().isForbidden());
		mvc.perform(get("/reports/")).andExpect(status().isForbidden());
		
		mvc.perform(get("/project/view/{id}/", 777)).andExpect(status().isForbidden());
		mvc.perform(get("/project/view/{id}/report/", 777)).andExpect(status().isForbidden());
		mvc.perform(post("/project/create/",
						"/project/delete/",
						"/project/update/").param("id", "777")).andExpect(status().isForbidden());
		mvc.perform(post("/project/addTrait/",
				"/project/deleteTrait/",
				"/project/addTraitCategory/",
				"/project/deleteTraitCategory/").param("projectId", "777")).andExpect(status().isForbidden());
		
		//Team
		mvc.perform(get("/team/view/{id}/", 777)).andExpect(status().isForbidden());
		mvc.perform(post("/team/create/").param("projectId", "777")).andExpect(status().isForbidden());
		mvc.perform(post("/team/update/",
				"/team/delete/",
				"/team/addUser/,",
				"/team/deleteUser/").param("teamId", "777")).andExpect(status().isForbidden());
		
		//Meeting
		mvc.perform(get("/meeting/view/{id}/", 777)).andExpect(status().isForbidden());
		mvc.perform(post("/meeting/create/",
				"/meeting/createSeveral/").param("teamId", "777")).andExpect(status().isForbidden());
		mvc.perform(post("/meeting/update/",
				"/meeting/delete/").param("id", "777")).andExpect(status().isForbidden());
		mvc.perform(post("/meeting/addParticipant/",
				"/meeting/deleteParticipant/").param("meetingId", "777")).andExpect(status().isForbidden());
		
		//Meeting Feedback
		mvc.perform(get("/meetingFeedback/view/{id}/", 777)).andExpect(status().isForbidden());
		mvc.perform(get("/meetingFeedback/edit/{meetingFeedbackId}/", 777)).andExpect(status().isForbidden());
		mvc.perform(get("/meetingFeedback/create/{studentId}/{meetingId}/", 777, 777)).andExpect(status().isForbidden());
		
		mvc.perform(post("/meetingFeedback/create/").param("meetingId", "777")).andExpect(status().isForbidden());
		mvc.perform(post("/meetingFeedback/update/",
				"/meetingFeedback/delete/").param("id", "777")).andExpect(status().isForbidden());
		
		//HrFeedback
		mvc.perform(get("/hrFeedback/")).andExpect(status().isForbidden());
		
		//User
		mvc.perform(get("/user/view/{id}/",777)).andExpect(status().isForbidden());
		mvc.perform(post("/user/changeStatus/").param("teamId", "777")).andExpect(status().isForbidden());
		
		mvc.perform(post("/user/allWithRole/mentor/view/",
				"/allFreeWithRole/{role}/view/","student")).andExpect(status().is3xxRedirection());
		
	}
	
}

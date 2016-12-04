package nc.ukma.thor.spms.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import nc.ukma.thor.spms.service.impl.UserDetailsServiceImpl;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
 
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    
    @Autowired
    public void registerGlobalAuthentication(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(getBCryptPasswordEncoder());
    }
 
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.authorizeRequests().antMatchers("/resources/**").permitAll();
    	http
    		//.csrf().disable()
			.authorizeRequests()
				.antMatchers("/traitManager/**",
							"/traitCategory/**",
							"/trait/**").hasAuthority("admin")
				.antMatchers("/reports/**").hasAnyAuthority("admin","hr")
			//Project
				.antMatchers("/project/view/{id}/").access("hasAnyAuthority({'admin','hr'}) "
						+ "|| (hasAuthority('mentor') && (@spmsWebSecurityService.isUserMemberOfProject(principal, #id) "
														+ "|| @spmsWebSecurityService.isUserChiefMentorOfProject(principal, #id)))")
				.antMatchers("/project/view/{id}/report/").access("hasAnyAuthority({'admin','hr'}) "
						+ "|| (hasAuthority('mentor') && @spmsWebSecurityService.isUserChiefMentorOfProject(principal, #id))")
				.antMatchers("/project/create/",
							"/project/delete/").hasAuthority("admin")
				.antMatchers("/project/update/").access("hasAuthority('admin') "
						+ "|| (hasAuthority('mentor')"
							+ "&& @spmsWebSecurityService.isUserChiefMentorOfProject(principal, request.getParameter('id')))")

				.antMatchers("/project/addTrait/",
							"/project/deleteTrait/",
							"/project/addTraitCategory/",
							"/project/deleteTraitCategory/").access("hasAuthority('admin') "
						+ "|| (hasAuthority('mentor')"
							+ "&& @spmsWebSecurityService.isUserChiefMentorOfProject(principal, request.getParameter('projectId')))")
			//Team
				
				.antMatchers("/team/view/{id}/").access("hasAnyAuthority({'admin','hr'}) "
														+ "|| (hasAuthority('mentor') && (@spmsWebSecurityService.isUserMemberOfTeam(principal, #id) "
															+ "|| @spmsWebSecurityService.isUserChiefMentorOfProjectWithTeam(principal, #id)))")
				.antMatchers("/team/create/").access("hasAuthority('admin') "
						+ "|| (hasAuthority('mentor')"
							+ "&& @spmsWebSecurityService.isUserChiefMentorOfProject(principal, request.getParameter('projectId')))")
				.antMatchers("/team/update/",
							"/team/delete/",
							"/team/addUser/,",
							"/team/deleteUser/").access("hasAuthority('admin') "
									+ "|| (hasAuthority('mentor')"
										+ "&& @spmsWebSecurityService.isUserChiefMentorOfProjectWithTeam(principal, request.getParameter('teamId')))")
			
			//Meeting
				.antMatchers("/meeting/view/{id}/").access("hasAnyAuthority({'admin','hr'}) "
						+ "|| (hasAuthority('mentor') && (@spmsWebSecurityService.isUserMemberOfTeamWithMeeting(principal, #id) "
							+ "|| @spmsWebSecurityService.isUserChiefMentorOfProjectWithMeeting(principal, #id)))")
				
				.antMatchers("/meeting/create/",
							"/meeting/createSeveral/").access("hasAuthority('admin') "
									+ "|| (hasAuthority('mentor') && (@spmsWebSecurityService.isUserMemberOfTeam(principal, request.getParameter('teamId')) "
										+ "|| @spmsWebSecurityService.isUserChiefMentorOfProjectWithTeam(principal, request.getParameter('teamId'))))")
			
				.antMatchers("/meeting/update/",
							"/meeting/delete/").access("hasAuthority('admin') "
									+ "|| (hasAuthority('mentor') && (@spmsWebSecurityService.isUserMemberOfTeamWithMeeting(principal, request.getParameter('id')) "
										+ "|| @spmsWebSecurityService.isUserChiefMentorOfProjectWithMeeting(principal, request.getParameter('id'))))")
				
				.antMatchers("/meeting/addParticipant/",
							"/meeting/deleteParticipant/").access("hasAuthority('admin') "
									+ "|| (hasAuthority('mentor') && (@spmsWebSecurityService.isUserMemberOfTeamWithMeeting(principal, request.getParameter('meetingId')) "
										+ "|| @spmsWebSecurityService.isUserChiefMentorOfProjectWithMeeting(principal, request.getParameter('meetingId'))))")
			//MeetingFeedback
				.antMatchers("/meetingFeedback/view/{id}/").access("hasAnyAuthority({'admin','hr'}) "
						+ "|| (hasAuthority('mentor') && (@spmsWebSecurityService.isUserMemberOfTeamWithMeetingFeedback(principal, #id) "
							+ "|| @spmsWebSecurityService.isUserChiefMentorOfProjectWithMeetingFeedback(principal, #id)))")
				
				.antMatchers("/meetingFeedback/create/{studentId}/{meetingId}/").access("hasAuthority('admin') "
						+ "|| (hasAuthority('mentor') && (@spmsWebSecurityService.isUserMemberOfTeamWithMeeting(principal, #meetingId) "
							+ "|| @spmsWebSecurityService.isUserChiefMentorOfProjectWithMeeting(principal, #meetingId)))")
				
				.antMatchers("/meetingFeedback/create/").access("hasAuthority('admin') "
						+ "|| (hasAuthority('mentor') && (@spmsWebSecurityService.isUserMemberOfTeamWithMeeting(principal, request.getParameter('meetingId')) "
							+ "|| @spmsWebSecurityService.isUserChiefMentorOfProjectWithMeeting(principal, request.getParameter('meetingId'))))")
				
				.antMatchers("/meetingFeedback/edit/{meetingFeedbackId}/").access("hasAuthority('admin') "
						+ "|| (hasAuthority('mentor') && (@spmsWebSecurityService.isUserMemberOfTeamWithMeetingFeedback(principal, #meetingFeedbackId) "
							+ "|| @spmsWebSecurityService.isUserChiefMentorOfProjectWithMeetingFeedback(principal, #meetingFeedbackId)))")
				
				.antMatchers("/meetingFeedback/update/",
							"/meetingFeedback/delete/").access("hasAuthority('admin') "
									+ "|| (hasAuthority('mentor') && (@spmsWebSecurityService.isUserMemberOfTeamWithMeetingFeedback(principal, request.getParameter('id')) "
										+ "|| @spmsWebSecurityService.isUserChiefMentorOfProjectWithMeetingFeedback(principal, request.getParameter('id'))))")
			//HrFeedback
				.antMatchers("/hrFeedback/**").hasAnyAuthority("admin","hr")
			//User
				.antMatchers("/user/changeStatus/").access("hasAnyAuthority({'admin','hr'}) "
						+ "|| (hasAuthority('mentor') && (@spmsWebSecurityService.isUserMemberOfTeam(principal, request.getParameter('teamId')) "
						+ "|| @spmsWebSecurityService.isUserChiefMentorOfProjectWithTeam(principal, request.getParameter('teamId'))))")
			
				.antMatchers("/user/view/{id}/").access("hasAnyAuthority({'admin','hr'}) "
						+ "|| (hasAuthority('mentor') && (@spmsWebSecurityService.isUserMemberOfTeamWithMember(principal, #id) "
							+ "|| @spmsWebSecurityService.isUserChiefMentorOfProjectWithMember(principal, #id)))")
			
				.antMatchers("/user/all/view/",
							"/user/allWithRole/student/view/").hasAnyAuthority("admin","hr")
				
				.antMatchers("/user/allWithRole/mentor/view/",
							"/allFreeWithRole/{role}/view/").access("hasAnyAuthority({'admin','hr'}) "
									+ "|| (hasAuthority('mentor') && @spmsWebSecurityService.isUserChiefMentor(principal)) ")

				//.anyRequest().denyAll()
				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/authentication")
				.loginProcessingUrl("/login")
				.failureUrl("/authentication?error")
				.usernameParameter("userName")
				.passwordParameter("userPassword")
				.permitAll()
	   			.and()
			.logout()
	   	        .permitAll()
	   	        .logoutUrl("/logout")
	   	        .logoutSuccessUrl("/authentication?logout")
	   	        .invalidateHttpSession(true)
	   	        .and()
	   		.exceptionHandling()
	   			.accessDeniedPage("/403/")
	   			.and();
			   	
    }
        
    public BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
 
}


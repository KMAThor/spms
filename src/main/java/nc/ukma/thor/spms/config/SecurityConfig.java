package nc.ukma.thor.spms.config;

import nc.ukma.thor.spms.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
//import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
 
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
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
    	http.authorizeRequests().antMatchers("/project/4/**").permitAll();
    	http
    		.csrf().disable()
			.authorizeRequests()
				.antMatchers("/traitManager/**").hasAuthority("admin")
				.antMatchers("/create/project/","/update/project/{id}", "/delete/project/{id}").hasAuthority("admin")
				.antMatchers("/{project_id}/create/team/","/delete/team/{id}/","/update/team/{id}/").hasAuthority("admin")
				.antMatchers("/{team_id}/addUser/{user_id}/","/{team_id}/deleteUser/{user_id}/").hasAuthority("admin")//add mentor
				.antMatchers("/team/{id}/add/meeting/","/team/{id}/meetings/{meeting_id}/{user_id}/Review").hasAnyAuthority("admin","mentor")
				.antMatchers("/project/{id}/upload/files/","/project/{id}/delete/files/").hasAnyAuthority("admin","mentor")
				.antMatchers("/{team_id}/{user_id}/status","/{team_id}/{user_id}/feedback").hasAnyAuthority("admin","hr")
				

				.anyRequest().authenticated()
				.and()
			.formLogin()
				.loginPage("/authentication")
				.loginProcessingUrl("/j_spring_security_check")
				.failureUrl("/authentication?error")
				.usernameParameter("j_username")
				.passwordParameter("j_password")
				.permitAll()
	   			.and()
			.logout()
	   	        .permitAll()
	   	        .logoutUrl("/j_spring_security_logout")// .logoutUrl("/j_spring_security_logout")
	   	        .logoutSuccessUrl("/authentication?logout") //or "/authentication?logout"
	   	        .invalidateHttpSession(true)
	   			.and();
			   	
    }
        
    public BCryptPasswordEncoder getBCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
 
}


package nc.ukma.thor.spms.config;

import nc.ukma.thor.spms.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
    	http
    		.csrf().disable()
			.authorizeRequests()
				.antMatchers("/traitManager/**").hasAuthority("admin")

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


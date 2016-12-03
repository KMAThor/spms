package nc.ukma.thor.spms.service.impl;

import nc.ukma.thor.spms.entity.Role;
import nc.ukma.thor.spms.entity.SpmsUserDetails;
import nc.ukma.thor.spms.entity.User;
import nc.ukma.thor.spms.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
 
import java.util.HashSet;
import java.util.Set;
 
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    private UserService userService;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	User user = userService.getUser(email);
    	if(user == null || user.getRole() == Role.STUDENT)
    		throw new UsernameNotFoundException("User with username "+ email +" not found");
        Set<GrantedAuthority> roles = new HashSet<>();
        roles.add(new SimpleGrantedAuthority(user.getRole().getName()));
        UserDetails userDetails = new SpmsUserDetails((long) user.getId(),
        		user.getEmail(), user.getPassword(), roles);
        return userDetails;
    }
 
}
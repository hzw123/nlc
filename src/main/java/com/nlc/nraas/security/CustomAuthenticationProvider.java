package com.nlc.nraas.security;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.nlc.nraas.domain.Role;
import com.nlc.nraas.domain.User;
import com.nlc.nraas.enums.UserStatus;
import com.nlc.nraas.repo.UserRepository;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

//	private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);
	private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private UserRepository userRepository;
    
	@Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();
        List<GrantedAuthority> grantedAuths = new ArrayList<>();
        
        logger.debug("username is " + name);
        
        User user = userRepository.findByName(name);
        if (user == null || name == null || password == null) {
        	throw new BadCredentialsException("Username not found.");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        
        if (user.getStatus() != UserStatus.ENABLE) {
        	throw new BadCredentialsException("Wrong status.");
        }
        if(!bCryptPasswordEncoder.matches(password,user.getPassword())){
            throw new BadCredentialsException("Wrong password.");
        }
        Set<Role> roles = user.getRoles();
//        if (roles.isEmpty()) {
//        	throw new BadCredentialsException("Role info is missing.");
//        }
        for (Role role : roles) {
            grantedAuths.add(new SimpleGrantedAuthority(role.getName()));
            logger.debug("username is " + name + ", " + role.getName());
        }
        
        return new UsernamePasswordAuthenticationToken(name, password, grantedAuths);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}

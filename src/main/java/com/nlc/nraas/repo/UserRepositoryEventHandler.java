package com.nlc.nraas.repo;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.nlc.nraas.domain.User;

@Component
@RepositoryEventHandler(User.class)
public class UserRepositoryEventHandler {

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @HandleBeforeCreate
    public void handleUserCreate(User user) {
    	if (!StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setCreateAt(new Date());
    	}
    }
}
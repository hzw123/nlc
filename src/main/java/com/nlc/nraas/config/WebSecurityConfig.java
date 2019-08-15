package com.nlc.nraas.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.nlc.nraas.security.CustomAuthenticationProvider;
import com.nlc.nraas.security.RESTAuthenticationEntryPoint;
import com.nlc.nraas.security.RESTAuthenticationFailureHandler;
import com.nlc.nraas.security.RESTAuthenticationSuccessHandler;
import com.nlc.nraas.security.RESTLogoutSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
    private CustomAuthenticationProvider customAuthenticationProvider;
	
	@Autowired
    private RESTAuthenticationEntryPoint authenticationEntryPoint;
	
	@Autowired
	private RESTAuthenticationFailureHandler failureHandler;
	
	@Autowired
	private RESTAuthenticationSuccessHandler successHandler;
	
	@Autowired
	RESTLogoutSuccessHandler logoutSuccessHandler;
    

    @Override  
    protected void configure(AuthenticationManagerBuilder auth)  
            throws Exception {   
        auth.authenticationProvider(customAuthenticationProvider);
    } 

    @Override 
    public void configure(WebSecurity web) throws Exception {
 		web.ignoring()
 		// Spring Security should completely ignore URLs starting with /resources/
// 			.antMatchers("/", "/**.html", "/css/**", "/bootstrap/**", "/font-awesome/**", "/img/**","/js/**");
 			.antMatchers("/login.html", "/favicon.ico", "/css/**", "/bootstrap/**", "/font-awesome/**", "/img/**", "/js/**", "/fonts/**", "/profiles/**");
    }

    
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.headers().frameOptions().sameOrigin();
        http
        	.csrf().disable()
            .authorizeRequests()
//                .antMatchers("/", "/**.html", "/css/**", "/bootstrap/**", "/font-awesome/**", "/img/**","/js/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and()
            .formLogin()
        		.successHandler(successHandler)
        		.failureHandler(failureHandler)
//            	.loginPage("/login")			//此处如果打开，，则之前的定制的handler都不生效，，使用的是默认的行为
//            	.defaultSuccessUrl("/", true)
//              .permitAll()
                .and()
            .logout()
            	.logoutSuccessHandler(logoutSuccessHandler);
//            	.logoutSuccessUrl("/login")
//                .permitAll();
    	
//    	http.csrf().disable().authorizeRequests().anyRequest().permitAll();
//    	http.csrf().disable().authorizeRequests().anyRequest().authenticated();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth
//            .inMemoryAuthentication()
//                .withUser("user").password("password").roles("USER");
//        auth.inMemoryAuthentication().withUser("admin").password("admin")
//		.roles("ADMIN", "USER").and().withUser("user").password("user")
//		.roles("USER");
    }
}
package com.microservice.weighttrackerapp.tracker.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    protected void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("in28Minutes").password("{noop}password").roles("USER", "ADMIN");
        auth.inMemoryAuthentication().withUser("Tom").password("{noop}password").roles("USER", "ADMIN");
    }


    protected void configure(HttpSecurity http) throws Exception {
        // anything with survey in link needs to have a USER access
        http.httpBasic()
                .and().authorizeRequests()
                .antMatchers("/add-measure/**").hasRole("USER")// users can access
                .antMatchers("/update-measure/**").hasRole("USER")// users can access
                .antMatchers("/get-all-measures/**").hasRole("USER")
                .antMatchers("/**").hasRole("USER") // anything
                .antMatchers("/**").hasRole("ADMIN") // anything
                .anyRequest().authenticated().and().formLogin()
                .and().csrf().disable()
                .headers().frameOptions().disable();
    }
}



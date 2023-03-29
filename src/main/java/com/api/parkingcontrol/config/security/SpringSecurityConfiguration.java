package com.api.parkingcontrol.config.security;

import com.api.parkingcontrol.services.LoginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {

    private final LoginService loginService;

    public SpringSecurityConfiguration(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.httpBasic()
               .and()
               .authorizeRequests()
               .antMatchers("/api/users/").permitAll()
               .antMatchers("/api/login").permitAll()
               .anyRequest().authenticated()
               .and()
               .csrf().disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(loginService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

package com.api.parkingcontrol.config.security;

import com.api.parkingcontrol.services.LoginService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SpringSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private static final Log log = LogFactory.getLog(SpringSecurityConfiguration.class);
    private final LoginService loginService;

    public SpringSecurityConfiguration(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.httpBasic()
               .and()
               .addFilterBefore(jwtAuthenticationFilter().getFilter(), UsernamePasswordAuthenticationFilter.class)
               .authorizeRequests()
               .antMatchers("/api/users/").permitAll()
               .antMatchers("/api/login").permitAll()
               .antMatchers("/api/roles").permitAll()
               .antMatchers(HttpMethod.POST,"/api/pessoas").hasRole("ADMIN")
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

    @Bean
    public FilterRegistrationBean<JwtAuthenticationFilter> jwtAuthenticationFilter() {
        FilterRegistrationBean<JwtAuthenticationFilter> registrationBean = new FilterRegistrationBean<>();

        registrationBean.setFilter(new JwtAuthenticationFilter());
        registrationBean.addUrlPatterns("/api/roles"); // Especifica os endpoints que devem ser protegidos pelo filtro
        registrationBean.addUrlPatterns("/api/pessoas"); // Especifica os endpoints que devem ser protegidos pelo filtro
        registrationBean.setOrder(1); // Define a ordem em que o filtro deve ser executado em relação a outros filtros
        return registrationBean;
    }
}

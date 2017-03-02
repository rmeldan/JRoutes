package com.softserve.edu.jroutes.configuration;

import com.softserve.edu.jroutes.controller.UserDetailsServiceImpl;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ApplicationSecurity extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = Logger.getLogger(ApplicationSecurity.class);

    @Autowired
    @Qualifier("userDetailsServiceImpl")
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**");
        LOGGER.info("configure(): css added to ignore");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/adminHome/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/profile/**").access("hasAnyRole('ROLE_ADMIN','ROLE_USER', 'ROLE_SUPER-ADMIN')")
                .antMatchers("/routes/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/users/**").access("hasRole('ROLE_SUPER-ADMIN')")
                .antMatchers("/securityRoles/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/transport/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/countries/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/cities/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/routeconnection/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/profile/").access("hasRole('ROLE_ADMIN')").and()
                .formLogin().loginPage("/admin")
                .failureUrl("/admin")
                .defaultSuccessUrl("/")
                .loginProcessingUrl("/j_spring_security_check").usernameParameter("j_username").passwordParameter("j_password").and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/").and()
                .anonymous().authorities("ROLE_ANONYMOUS").principal("guest").and()
                .exceptionHandling().accessDeniedPage("/accessDenied");
        LOGGER.info("configure(): http configured");

    }

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder authenticationMgr) throws Exception {
        authenticationMgr.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder());
        LOGGER.info("configAuthentication(): AuthenticationManager configured");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
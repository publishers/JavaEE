package com.epam.filters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${web.security.static.resources}")
    private String[] staticSecurityResources;

    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;

//    @Autowired
//    private SecurityExpressionHandler<FilterInvocation> expressionHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(staticSecurityResources).permitAll()
                .antMatchers("/login/**").not().authenticated()
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
//                .expressionHandler(expressionHandler)
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successHandler((req, res, auth) -> res.sendRedirect("/index"))
                .defaultSuccessUrl("/index")
                .failureHandler((req, res, exp) -> {
                    String errMsg = "Unknown error - " + exp.getMessage();
                    if (exp.getClass().isAssignableFrom(BadCredentialsException.class)) {
                        errMsg = "Invalid username or password.";
                    }
                    res.sendRedirect("/login?errorMessage=" + errMsg);
                })
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}
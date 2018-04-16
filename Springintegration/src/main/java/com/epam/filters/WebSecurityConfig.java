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

    @Value("${web.login.page}")
    private String login;

    @Value("${web.login.params.username}")
    private String username;

    @Value("${web.login.params.password}")
    private String password;

    @Value("${web.security.page.login}")
    private String loginPage;

    @Value("${web.security.page.admin}")
    private String adminPage;

    @Value("${web.security.page.index}")
    private String defaultIndexPage;

    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(staticSecurityResources).permitAll()
                .antMatchers(loginPage).not().authenticated()
                .antMatchers(adminPage).access("hasRole('ROLE_ADMIN')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage(login)
                .usernameParameter(username)
                .passwordParameter(password)
                .successHandler((req, res, auth) -> res.sendRedirect(defaultIndexPage))
                .defaultSuccessUrl(defaultIndexPage)
                .failureHandler((req, res, exp) -> {
                    String errMsg = "Unknown error - " + exp.getMessage();
                    if (exp.getClass().isAssignableFrom(BadCredentialsException.class)) {
                        errMsg = "Invalid username or password.";
                    }
                    res.sendRedirect(login + "?errorMessage=" + errMsg);
                })
                .permitAll();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }
}
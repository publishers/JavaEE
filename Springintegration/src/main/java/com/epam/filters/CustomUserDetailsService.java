package com.epam.filters;

import com.epam.database.entity.Role;
import com.epam.service.RoleService;
import com.epam.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service("customUserDetailsService")
@EnableAutoConfiguration
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email)
            throws UsernameNotFoundException {
        com.epam.database.entity.User user = userService.selectUserByEmail(email);
        if (user == null)
            throw new UsernameNotFoundException("Email " + email + " doesn't exist!");

        String password = user.getPassword();

        return new User(email, password, getGrantedAuthorities(user));
    }

    private Set<GrantedAuthority> getGrantedAuthorities(com.epam.database.entity.User user) {
        Set<GrantedAuthority> authorities = new HashSet<>();
        Role userRole = roleService.findRoleById(user.getRoleId());

        authorities.add(new SimpleGrantedAuthority("ROLE_" + userRole.getRoleName().toUpperCase()));
        return authorities;
    }

}
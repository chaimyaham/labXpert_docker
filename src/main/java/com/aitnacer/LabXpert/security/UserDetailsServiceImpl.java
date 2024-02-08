package com.aitnacer.LabXpert.security;

import com.aitnacer.LabXpert.entity.Utilisateur;
import com.aitnacer.LabXpert.service.IUserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private IUserService userService;
    public UserDetailsServiceImpl (IUserService userService){
        this.userService=userService;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur user=userService.getUserByUserName(username);
        if(user==null) throw new UsernameNotFoundException("utilisateur not found");
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority(user.getRole().name())
        );
        User userDetails = new User(
                user.getUserName(),
                user.getPassword(),
                authorities
        );
        return userDetails;
    }
}

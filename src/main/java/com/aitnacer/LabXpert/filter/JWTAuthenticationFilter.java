package com.aitnacer.LabXpert.filter;

import com.aitnacer.LabXpert.helper.JWTHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.GrantedAuthority;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JWTHelper jwtHelper;
    private AuthenticationManager authenticationManager;
    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTHelper jwtHelper) {
        this.authenticationManager = authenticationManager;
        this.jwtHelper = jwtHelper;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username= request.getParameter("username");
        String password=request.getParameter("password");
        UsernamePasswordAuthenticationToken authenticationToken= new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(authenticationToken);

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        String jwtAccessToken = jwtHelper.generateAccessToken(user.getUsername(), user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        String jwtRefreshToken = jwtHelper.generateRefreshToken(user.getUsername());
        response.setContentType("application/json");
        addTokenToCookie(response, jwtAccessToken);
        new ObjectMapper().writeValue(response.getOutputStream(),jwtHelper.getTokensMaps(jwtAccessToken,jwtRefreshToken));
    }

    private void addTokenToCookie(HttpServletResponse response, String token) {
        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setMaxAge((int) TimeUnit.DAYS.toSeconds(1));
        cookie.setSecure(true);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

}

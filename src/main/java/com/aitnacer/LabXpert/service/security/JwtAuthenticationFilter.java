package com.aitnacer.LabXpert.service.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;
    @Override
    protected void doFilterInternal(
           @NotNull HttpServletRequest request,
           @NotNull HttpServletResponse response,
           @NotNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader =request.getHeader("Authorization");
        final String  jwt ;
        final  String usernamee;
        if(authHeader ==null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;

        }
        jwt=authHeader.substring(7);
   //TOdO usernamee =  extartct the user  name from JWT token;
        usernamee=jwtService.extractUsername(jwt);
        //check if the user is already authenticated or not  we use Securitycontextholder when the it's
        // null it means that the user is not authenticated yet
        if(usernamee != null && SecurityContextHolder.getContext().getAuthentication() == null){
            //check if we have the user on database
            UserDetails userDetails =this.userDetailsService.loadUserByUsername(usernamee);
            if(jwtService.isTokenValid(jwt, userDetails)){
                //if the token is valid we need to updatte the security contextHolder
                // and send the request to our dispatcher servlet

               // UsernamePasswordAuthenticationToken authenticationToken =new UsernamePasswordAuthenticationToken();

            }

        }


    }
}

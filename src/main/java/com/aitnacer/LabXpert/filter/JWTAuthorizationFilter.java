package com.aitnacer.LabXpert.filter;

import com.aitnacer.LabXpert.helper.JWTHelper;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import static com.aitnacer.LabXpert.utils.JWTUtil.AUTH_HEADER;
import static com.aitnacer.LabXpert.utils.JWTUtil.SECRET;

public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private JWTHelper jwttHelper;

    public JWTAuthorizationFilter(JWTHelper jwttHelper) {
        this.jwttHelper = jwttHelper;
    }
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

         String accessToken= jwttHelper.extractTokenFromHeaderIfExists(request.getHeader(AUTH_HEADER));
         if(accessToken != null){
             Algorithm algorithm =Algorithm.HMAC256(SECRET);
             JWTVerifier jwtVerifier = JWT.require(algorithm).build();
             DecodedJWT decodedJWT= jwtVerifier.verify(accessToken);
             String username =decodedJWT.getSubject();
             String[] roles =decodedJWT.getClaim("roles").asArray(String.class);
             Collection<GrantedAuthority> authorities = new ArrayList<>();
             for (String role : roles) {
                 authorities.add(new SimpleGrantedAuthority(role));
             }
             UsernamePasswordAuthenticationToken authenticationToken =new  UsernamePasswordAuthenticationToken(username,null,authorities);
             SecurityContextHolder.getContext().setAuthentication(authenticationToken);
             filterChain.doFilter(request,response);
         }else{
             filterChain.doFilter(request,response);
         }
    }
}

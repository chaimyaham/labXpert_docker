package com.aitnacer.LabXpert.controller;

import com.aitnacer.LabXpert.entity.Utilisateur;
import com.aitnacer.LabXpert.helper.JWTHelper;
import com.aitnacer.LabXpert.service.impl.UserServiceImpl;
import com.aitnacer.LabXpert.utils.Constant;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

import static com.aitnacer.LabXpert.utils.JWTUtil.AUTH_HEADER;
import static com.aitnacer.LabXpert.utils.JWTUtil.SECRET;

@RestController
@CrossOrigin
@RequestMapping(Constant.BASE_API_URL +"user")
@AllArgsConstructor
public class RefrechTokenController {

    @Autowired
    private JWTHelper jwtHelper;

    private final UserServiceImpl userServiceImpl;

    @GetMapping("/refresh-token")
    public void generateNewAccessToken(HttpServletRequest request, HttpServletResponse response) throws IOException{
        String jwtRefreshToken = jwtHelper.extractTokenFromHeaderIfExists(request.getHeader(AUTH_HEADER));
        if(jwtRefreshToken !=null){
            Algorithm algorithm= Algorithm.HMAC256(SECRET);
            JWTVerifier jwtVerifier= JWT.require(algorithm).build();
            DecodedJWT decodedJWT= jwtVerifier.verify(jwtRefreshToken);
            String username =decodedJWT.getSubject();
            Utilisateur user = userServiceImpl.getUserByUserName(username);
            String jwtAccessToken =jwtHelper.generateAccessToken(user.getUserName(), Collections.singletonList(user.getRole().name()));
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(),jwtHelper.getTokensMaps(jwtAccessToken,jwtRefreshToken));


        }else{
            throw  new RuntimeException("Refresh token required");
        }
    }
}
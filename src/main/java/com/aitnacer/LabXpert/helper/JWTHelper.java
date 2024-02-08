package com.aitnacer.LabXpert.helper;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.sun.org.apache.xerces.internal.util.SymbolTable;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.aitnacer.LabXpert.utils.JWTUtil.*;


@Component
public class JWTHelper {
    Algorithm algorithm = Algorithm.HMAC256(SECRET);

    // function pour générer un Token d'accès JWT
    public String generateAccessToken(String username, List<String> roles){
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRE_ACCESS_TOKEN))
                .withIssuer(ISSUER)
                .withClaim("roles",roles)
                .sign(algorithm);

    }
    // function pour générer a refrech token JWT
    public String generateRefreshToken(String username){
        return  JWT.create().withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis()+EXPIRE_REFRESH_TOKEN))
                .withIssuer(ISSUER)
                .sign(algorithm);

    }
    // function pour extraire token from the header s'il existe
    public String extractTokenFromHeaderIfExists(String authorizationHeader){
        if(authorizationHeader != null && authorizationHeader.startsWith(BEARER_PREFIX)){
            return  authorizationHeader.substring(BEARER_PREFIX.length());
        }
        return null;
    }
    // Method to get a map of key-value pairs representing the tokens (accessToken, refreshToken)
    public Map<String, String> getTokensMaps(String jwtAccessToken, String jwtRefreshToken){
        Map<String,String> idTokens = new HashMap<>();
        idTokens.put("accessToken",jwtAccessToken);
        idTokens.put("refrechToken",jwtRefreshToken);
        return  idTokens;
    }
}

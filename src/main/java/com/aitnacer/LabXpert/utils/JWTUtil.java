package com.aitnacer.LabXpert.utils;

public class JWTUtil {
    public static final long EXPIRE_ACCESS_TOKEN = 10*60*1000 ;
    public static final long EXPIRE_REFRESH_TOKEN = 10*60*1000 ;
    public static final String BEARER_PREFIX="Bearer ";
    public static final String ISSUER = "springBootApp";
    public  static final String SECRET = "the private secret";
    public static final String AUTH_HEADER = "Authorization";
}

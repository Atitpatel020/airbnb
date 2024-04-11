package com.schedular.utill;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.schedular.entity.UserRegistration;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;


@Component
public class JwtService {

    private static final String USER_NAME="username";

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;

    @Value("${jwt.issuer}")
    private String issuer;

    @Value("${jwt.expiry.duration}")
    private int expiryDuration;
    private Algorithm algorithm;

    @PostConstruct
    private void postConstruct(){
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    public String createToken(UserRegistration registration){
        return JWT.create()
                .withClaim(USER_NAME, registration.getUserName())
                .withExpiresAt(new Date(System.currentTimeMillis()+expiryDuration))
                .withIssuer(issuer)
                .withSubject(registration.getUserName())
                .sign(algorithm);
    }


    public String getUserName(String token) {
        DecodedJWT decodedJWT = JWT.require(algorithm).withIssuer(issuer).build().verify(token);
        return decodedJWT.getClaim(USER_NAME).asString();
    }
}

package com.hauntedplace.HauntedPlaceAPI.Services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.hauntedplace.HauntedPlaceAPI.Entitys.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;

@Service
public class JWTokenService {

    public String generateToken(User user){
        var algorithm = Algorithm.HMAC256("12345678");
        return JWT.create()
                .withIssuer("MT API")
                .withSubject(user.getName())
                .withExpiresAt(LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00")))
                .sign(algorithm);
    }
}

package com.hauntedplace.HauntedPlaceAPI.Security.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hauntedplace.HauntedPlaceAPI.Entitys.User;
import com.hauntedplace.HauntedPlaceAPI.Models.AuthData;
import com.hauntedplace.HauntedPlaceAPI.Services.JWTokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import java.io.IOException;

@Component
public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private final JWTokenService jwtTokenService;

    @Autowired
    public AuthenticationFilter(AuthenticationManager authenticationManager, JWTokenService jwtTokenService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthData data  = new ObjectMapper().readValue(request.getInputStream(), AuthData.class);
            var authentication = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)  {
        User user = (User) authResult.getPrincipal();
        var tokenJWT = jwtTokenService.generateToken(user);
        response.addHeader("Authorization", "Bearer " + tokenJWT);
        response.addHeader("UserID", user.getId().toString());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}

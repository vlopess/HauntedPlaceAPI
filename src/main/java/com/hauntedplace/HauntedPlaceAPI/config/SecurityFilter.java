package com.hauntedplace.HauntedPlaceAPI.config;

import com.hauntedplace.HauntedPlaceAPI.Repository.UserRepository;
import com.hauntedplace.HauntedPlaceAPI.Services.JWTokenService;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/api/**")
public class SecurityFilter extends OncePerRequestFilter {

    private final JWTokenService jwTokenService;
    private final UserRepository userRepository;

    @Autowired
    public SecurityFilter(JWTokenService jwTokenService, UserRepository userRepository) {
        this.jwTokenService = jwTokenService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var tokenJWT = getToken(request);
        if (tokenJWT != null) {
            var email = jwTokenService.verifyToken(tokenJWT);
            var user = userRepository.findByUsername(email);
            var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }

    private String getToken(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if (token == null || !token.startsWith("Bearer ")) {
            return null;
        }
        return token.replace("Bearer ", "");
    }
}

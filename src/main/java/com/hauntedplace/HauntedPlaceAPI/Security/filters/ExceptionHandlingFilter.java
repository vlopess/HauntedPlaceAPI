package com.hauntedplace.HauntedPlaceAPI.Security.filters;

import com.hauntedplace.HauntedPlaceAPI.Services.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class ExceptionHandlingFilter extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        }catch (EntityNotFoundException e){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            response.getWriter().write("User not found");
            response.getWriter().flush();
        }catch (RuntimeException e){
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write(e.getMessage());
        }
    }
}

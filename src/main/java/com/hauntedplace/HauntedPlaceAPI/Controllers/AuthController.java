package com.hauntedplace.HauntedPlaceAPI.Controllers;

import com.hauntedplace.HauntedPlaceAPI.DTOS.TokenJWTData;
import com.hauntedplace.HauntedPlaceAPI.Entitys.User;
import com.hauntedplace.HauntedPlaceAPI.Models.AuthData;
import com.hauntedplace.HauntedPlaceAPI.Services.JWTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class AuthController {
    final private AuthenticationManager authenticationManager;
    final private JWTokenService jwtokenService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, JWTokenService jwtokenService){
        this.authenticationManager = authenticationManager;
        this.jwtokenService = jwtokenService;
    }

    @PostMapping
    public ResponseEntity<Object> login(@RequestBody AuthData data){
        try {
            var token = new UsernamePasswordAuthenticationToken(data.email(), data.password());
            var authentication = authenticationManager.authenticate(token);
            User user = (User) authentication.getPrincipal();
            var tokenJWT = jwtokenService.generateToken(user);
            return ResponseEntity.ok(new TokenJWTData(tokenJWT, user.getId()));
        } catch (InternalAuthenticationServiceException e){
            return null;
                    //ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}

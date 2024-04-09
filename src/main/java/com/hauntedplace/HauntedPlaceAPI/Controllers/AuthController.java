package com.hauntedplace.HauntedPlaceAPI.Controllers;

import com.hauntedplace.HauntedPlaceAPI.Entitys.User;
import com.hauntedplace.HauntedPlaceAPI.Models.AuthData;
import com.hauntedplace.HauntedPlaceAPI.Services.JWTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    @GetMapping
    public ResponseEntity<Object> login(@RequestBody AuthData data){
        var token = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var authentication = authenticationManager.authenticate(token);
        return ResponseEntity.ok(jwtokenService.generateToken((User) authentication.getPrincipal()));
    }
}

package com.hauntedplace.HauntedPlaceAPI.Controllers;

import com.hauntedplace.HauntedPlaceAPI.DTOS.TokenJWTData;
import com.hauntedplace.HauntedPlaceAPI.Entitys.User;
import com.hauntedplace.HauntedPlaceAPI.Models.AuthData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/authenticate")
public class AuthController {
    final private AuthenticationManager authenticationManager;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }


    @PostMapping
    public ResponseEntity<Object> login(@RequestBody AuthData data){
        var token = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var authentication = authenticationManager.authenticate(token);
        User user = (User) authentication.getPrincipal();
        return ResponseEntity.ok(new TokenJWTData("tokenJWT", user.getId()));
    }
}

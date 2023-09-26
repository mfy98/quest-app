package com.filxdev.questapp.controllers;

import com.filxdev.questapp.entities.User;
import com.filxdev.questapp.requests.UserRequest;
import com.filxdev.questapp.security.JwtTokenProvider;
import com.filxdev.questapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;

    private UserService userService;

    private PasswordEncoder passwordEncoder;

    public AuthController(AuthenticationManager authenticationManager, UserService userService){
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }
    @PostMapping("/login")
    public String login(@RequestBody UserRequest loginRequest){
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(loginRequest.getUserName(),loginRequest.getPassword());
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return "Bearer " + jwtTokenProvider.generateJwtToken(auth);

    }
    @PostMapping("/register")
    public ResponseEntity<String> register (@RequestBody UserRequest registerRequest){
        //Response Entity'nin amacı, register olup olmadığımızın bilgisinin Header'da verilmesi.
        if(userService.getOneUserByUserName(registerRequest.getUserName()) != null){
            return new ResponseEntity<>("Username already in use!", HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUserName(registerRequest.getUserName());
        user.setPassword(registerRequest.getPassword());
        userService.saveOneUser(user);
        return new ResponseEntity<>("User successfully registered!", HttpStatus.CREATED);
    }
}

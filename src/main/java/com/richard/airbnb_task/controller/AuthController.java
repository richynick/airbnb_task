package com.richard.airbnb_task.controller;

import com.richard.airbnb_task.config.JwtProvider;
import com.richard.airbnb_task.model.User;
import com.richard.airbnb_task.reponse.AuthResponse;
import com.richard.airbnb_task.request.LoginRequest;
import com.richard.airbnb_task.request.UserRequest;
import com.richard.airbnb_task.respository.UserRepository;
import com.richard.airbnb_task.service.CustomeUserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CustomeUserDetailsImpl customeUserDetails;

    @PostMapping("/signup")
    public ResponseEntity<AuthResponse> createUser(@RequestBody UserRequest request) throws Exception{
        User createdUser = new User();
        createdUser.setPassword(passwordEncoder.encode(request.getPassword()));
        createdUser.setUsername(request.getUsername());
        createdUser.setFullName(request.getFullName());
        createdUser.setEmail(request.getEmail());
        createdUser.setPhoneNumber(request.getPhoneNumber());

        User savedUser = userRepository.save(createdUser);

        Authentication authentication = new UsernamePasswordAuthenticationToken(request.getUsername(),request.getPassword());
        SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = JwtProvider.generateToken(authentication);
            AuthResponse res = new AuthResponse();
            res.setJwt(jwt);
            res.setMessage("signup successful");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest){
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

//        Authentication authentication = authenticate(username,password);
        Authentication authentication = new UsernamePasswordAuthenticationToken(username,password);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = JwtProvider.generateToken(authentication);
        AuthResponse res = new AuthResponse();
        res.setJwt(jwt);
        res.setMessage("signing successful");

        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }
    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customeUserDetails.loadUserByUsername(username);
        if(userDetails==null){
            throw new BadCredentialsException("invalid username");
        }
        if( !passwordEncoder.matches(password,userDetails.getPassword())){
            throw new BadCredentialsException("invalid password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails,null,null);
    }
}

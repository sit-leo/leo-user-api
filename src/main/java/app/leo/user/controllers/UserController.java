package app.leo.user.controllers;


import app.leo.user.DTO.Token;
import app.leo.user.DTO.UserLoginRequest;
import app.leo.user.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin("*")
@RestController
public class UserController {
    @Autowired
    TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Token> createRecruiterRanking(@Valid @RequestBody UserLoginRequest userLoginRequest){
        Token token = new Token();
        return  new ResponseEntity<>(token, HttpStatus.CREATED);
    }
}

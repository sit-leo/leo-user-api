package app.leo.user.controllers;


import app.leo.user.DTO.Token;
import app.leo.user.DTO.UserLoginRequest;
import app.leo.user.models.User;
import app.leo.user.services.TokenService;
import app.leo.user.services.UserService;
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

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Token> login(@Valid @RequestBody UserLoginRequest userLoginRequest){
        User user = this.userService.findByUsername(userLoginRequest.getUsername());
        // check password is equal
        return  new ResponseEntity<>(tokenService.generateTokenByUser(user), HttpStatus.CREATED);
    }
}

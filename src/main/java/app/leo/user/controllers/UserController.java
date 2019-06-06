package app.leo.user.controllers;


import app.leo.user.DTO.Token;
import app.leo.user.DTO.UserLoginRequest;
import app.leo.user.exceptions.UserNotFoundException;
import app.leo.user.exceptions.WrongPasswordException;
import app.leo.user.models.User;
import app.leo.user.services.TokenService;
import app.leo.user.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
        if(user == null){
            throw new UserNotFoundException("User is not found");
        }
        boolean passwordIsCorrect = user.getPassword().equals(userLoginRequest.getPassword());
        if(passwordIsCorrect){
            return  new ResponseEntity<>(tokenService.generateTokenByUser(user), HttpStatus.CREATED);
        }else{
            throw new WrongPasswordException("Wrong username or password");
        }
    }

    @GetMapping("/me")
    public ResponseEntity<User> getUser(
            @RequestHeader(name = "Authorization", required = true) String token,
            HttpServletRequest request
    ) {
        String username = tokenService.getUsernameFromToken(token);
        User user = userService.findByUsername(username);
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }
}

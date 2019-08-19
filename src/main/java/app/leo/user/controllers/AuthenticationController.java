package app.leo.user.controllers;

import app.leo.user.DTO.TokenDTO;
import app.leo.user.DTO.UserLoginRequest;
import app.leo.user.exceptions.UserNotFoundException;
import app.leo.user.exceptions.WrongPasswordException;
import app.leo.user.models.User;
import app.leo.user.services.AuthenticationService;
import app.leo.user.services.TokenService;
import app.leo.user.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@CrossOrigin("*")
public class AuthenticationController {
    @Autowired
    TokenService tokenService;
    @Autowired
    AuthenticationService authenticationService;
    @Autowired
    UserService userService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@Valid @RequestBody UserLoginRequest userLoginRequest){
        User user = this.userService.findByUsername(userLoginRequest.getUsername());
        if(user == null){
            throw new UserNotFoundException("User is not found");
        }
        if(authenticationService.passwordIsCorrect(user.getPassword(),userLoginRequest.getPassword())){
            TokenDTO tokenDTO = modelMapper.map(tokenService.generateTokenByUser(user),TokenDTO.class);
            return  new ResponseEntity<>(tokenDTO, HttpStatus.CREATED);
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

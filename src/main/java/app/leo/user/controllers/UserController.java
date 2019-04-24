package app.leo.user.controllers;


import app.leo.user.services.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
public class UserController {
    @Autowired
    TokenService tokenService;
}

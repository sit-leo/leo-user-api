package app.leo.user.controllers;

import app.leo.user.models.RefreshToken;
import app.leo.user.services.RefreshTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

    @Autowired
    private RefreshTokenService refreshTokenService;

    @PostMapping("/add")
    public ResponseEntity<String> add(RefreshToken token){
        refreshTokenService.createRefreshToken(token);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<RefreshToken> get(String username){
        return new ResponseEntity<>(refreshTokenService.getRefreshToken(username),HttpStatus.OK);
    }
}

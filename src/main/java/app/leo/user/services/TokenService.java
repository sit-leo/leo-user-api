package app.leo.user.services;

import app.leo.user.DTO.Token;

import app.leo.user.models.User;
import app.leo.user.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {
    @Autowired
    UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expires}")
    private String expires;

    public Token generateTokenByUser(User user) {
        Claims claims = Jwts.claims()
                .setSubject(user.getUsername());
        claims.put("userId", String.valueOf(user.getId()));
        claims.put("role", user.getRole());

        Token token = new Token();
        token.setToken(
            Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()
        );
        return token ;
    }
}

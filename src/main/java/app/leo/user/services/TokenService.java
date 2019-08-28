package app.leo.user.services;

import app.leo.user.exceptions.InvalidTokenException;
import app.leo.user.models.Token;
import app.leo.user.repositories.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import app.leo.user.models.User;
import app.leo.user.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

@Service
public class TokenService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    TokenRepository tokenRepository;

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expires}")
    private long expires;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    public Token generateTokenByUser(User user) {
        String username = user.getUsername();
        Claims claims = Jwts.claims()
                .setSubject(username);
        claims.put("userId", String.valueOf(user.getId()));
        claims.put("role", user.getRole());
        Token token = new Token();
        token.setToken(
            Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact()
        );
        token.setToken("Bearer "+token.getToken());
        if(sessionIsExist(username,token.getToken())){
            return getTokenByUsernameAndToken(username,token.getToken());
        }
        token.setUsername(username);
        token.setExpiresTime(new Date(System.currentTimeMillis()+expires));
        tokenRepository.save(token);
        return token ;
    }

    public String getUsernameFromToken(String token) {
        if (token == null) {
            throw new InvalidTokenException("Token is null");
        } else if (!token.startsWith("Bearer") ||token.length() < 7) {
            throw new InvalidTokenException("Token is invalid");
        }
        String tokenFormat = token.substring(7);
        Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(tokenFormat);
        return (String) claims.getBody().get("sub");
    }

    private boolean sessionIsExist(String username, String token){
        Token result = getTokenByUsernameAndToken(username,token);
        if(result == null){
            return false;
        }else if(isExpires(result)){
            LogOut(username);
            return false;
        }
        return true;
    }

    private boolean isExpires(Token token){
        return token.getExpiresTime().before(new Date(System.currentTimeMillis()));
    }

    public void LogOut(String username) {
        tokenRepository.deleteByUsername(username);
    }

    public Token getTokenByUsernameAndToken(String username,String token){
        return tokenRepository.findByUsernameAndToken(username,token);
    }


}

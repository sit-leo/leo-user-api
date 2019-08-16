package app.leo.user.services;

import app.leo.user.models.RefreshToken;
import app.leo.user.repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public void createRefreshToken(RefreshToken refreshToken){
        refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken getRefreshTokenByUsername(String username){
        return refreshTokenRepository.findByUsername(username);
    }

}

package app.leo.user.services;

import app.leo.user.models.RefreshToken;
import app.leo.user.repositories.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RefreshTokenService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    public void createRefreshToken(RefreshToken refreshToken){
        refreshTokenRepository.addItem(refreshToken);
    }

    public RefreshToken getRefreshToken(String username){
        return refreshTokenRepository.getToken(username);
    }

}

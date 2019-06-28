package app.leo.user.repositories;

import app.leo.user.models.RefreshToken;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RefreshTokenRepository {
    public static final String KEY = "TOKENFUCKINGKEN";
    private RedisTemplate<String,RefreshToken> redisTemplate;
    private HashOperations hashOperations;

    public RefreshTokenRepository(RedisTemplate<String, RefreshToken> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    public void addItem(RefreshToken refreshToken){
        hashOperations.put(KEY,refreshToken.getUsername(),refreshToken);
    }

    public RefreshToken getToken(String username){
        return (RefreshToken) hashOperations.get(KEY,username);
    }
}

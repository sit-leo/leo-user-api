package app.leo.user.configs;

import app.leo.user.models.RefreshToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;


@Configuration
public class RegistConfig {
    @Bean
    JedisConnectionFactory jedisConnectionFactory() {
        return new JedisConnectionFactory();
    }
    @Bean
    public RedisTemplate<String, RefreshToken> redisTemplate() {
        RedisTemplate<String, RefreshToken> template = new RedisTemplate<>();
        template.setConnectionFactory(jedisConnectionFactory());
        return template;
    }
    @Bean
    public RedisCacheConfiguration cacheConfiguration() {
        RedisCacheConfiguration cacheConfig = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(10))
                .disableCachingNullValues();
        return cacheConfig;
    }
    @Bean
    public RedisCacheManager cacheManager() {
        RedisCacheManager rcm = RedisCacheManager.builder(jedisConnectionFactory())
                .cacheDefaults(cacheConfiguration())
                .transactionAware()
                .build();
        return rcm;
    }

}


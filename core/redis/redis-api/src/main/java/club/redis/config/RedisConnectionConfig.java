package club.redis.config;

import club.redis.properties.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedisConnectionConfig {
    
    @Bean
    public RedisConnectionFactory redisConnectionFactory(RedisProperties redisProperties) {
        // cluster
        if (redisProperties.useClusterMode()) {
            RedisClusterConfiguration cfg = new RedisClusterConfiguration(redisProperties.nodes());
            applyAuth(cfg, redisProperties);
            return new LettuceConnectionFactory(cfg);
        }
        
        // standalone
        String[] hp = redisProperties.nodes().getFirst().split(":", 2);
        String host = hp[0];
        int port = Integer.parseInt(hp[1]);
        
        RedisStandaloneConfiguration cfg = new RedisStandaloneConfiguration(host, port);
        applyAuth(cfg, redisProperties);
        return new LettuceConnectionFactory(cfg);
    }
    
    private void applyAuth(RedisStandaloneConfiguration cfg, RedisProperties redisProperties) {
        if (redisProperties.username() != null && !redisProperties.username().isBlank()) {
            cfg.setUsername(redisProperties.username());
        }
        if (redisProperties.password() != null && !redisProperties.password().isBlank()) {
            cfg.setPassword(RedisPassword.of(redisProperties.password()));
        }
    }
    
    private void applyAuth(RedisClusterConfiguration cfg, RedisProperties redisProperties) {
        if (redisProperties.username() != null && !redisProperties.username().isBlank()) {
            cfg.setUsername(redisProperties.username());
        }
        if (redisProperties.password() != null && !redisProperties.password().isBlank()) {
            cfg.setPassword(RedisPassword.of(redisProperties.password()));
        }
    }
}

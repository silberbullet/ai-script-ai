package club.auth.jwt.adapter;

import club.auth.port.GenerateTokenPort;
import club.jwt.issuer.JwtIssuer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AuthJwtTokenAdapter implements GenerateTokenPort {
    
    private final JwtIssuer issuer;
    
    @Override
    public String generateAccessToken(String userId, int ACCESS_TOKEN_EXPIRATION) {
        Map<String, Object> claims = Map.of(
                "userId", userId
        );
        return issuer.issue(userId, claims, ACCESS_TOKEN_EXPIRATION);
    }
    
    @Override
    public String hashSha256(String token) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(token.getBytes(StandardCharsets.UTF_8));
            
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public String generateSecureRandom() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);
    }
}

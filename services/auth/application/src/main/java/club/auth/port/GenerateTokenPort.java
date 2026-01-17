package club.auth.port;

public interface GenerateTokenPort {
    /**
     * JWT 형식의 accessToken을 발급합니다.
     */
    String generateAccessToken(String userId, int ACCESS_TOKEN_EXPIRATION);
    
    /**
     * SHA-256 해시 함수를 사용하여 문자열을 해싱합니다.
     */
    String hashSha256(String token);
    
    /**
     * 암호학적으로 안전한 난수를 생성합니다. (uuid보다 안전)
     */
    String generateSecureRandom();
}

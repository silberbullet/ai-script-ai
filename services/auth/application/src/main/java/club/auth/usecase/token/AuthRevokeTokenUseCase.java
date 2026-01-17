package club.auth.usecase.token;

public interface AuthRevokeTokenUseCase {
    
    void revokeToken(String userId, String token);
}

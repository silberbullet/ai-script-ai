package club.auth.usecase.sign;

public interface AuthEmailCheckUseCase {
    
    boolean existsByEmail(String email);
}

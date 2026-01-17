package club.auth.port;

import club.auth.domain.User;

import java.util.Optional;
import java.util.function.Supplier;

public interface AuthCommandRepositoryPort {
    
    <T> T transaction(Supplier<T> T);
    
    User save(User user);
    
    Optional<User> findByEmail(String email);
    
    boolean existsByEmail(String email);
}

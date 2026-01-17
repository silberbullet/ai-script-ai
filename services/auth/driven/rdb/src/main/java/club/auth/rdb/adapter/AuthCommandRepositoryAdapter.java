package club.auth.rdb.adapter;

import java.util.Optional;
import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;
import club.auth.domain.User;
import club.auth.port.AuthCommandRepositoryPort;
import club.auth.rdb.entity.UserEntity;
import club.auth.rdb.mapper.UserEntityMapper;
import club.auth.rdb.repository.AuthJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AuthCommandRepositoryAdapter implements AuthCommandRepositoryPort {
    
    private final AuthJpaRepository authJpaRepository;
    private final UserEntityMapper mapper;
    
    @Override
    @Transactional
    public <T> T transaction(Supplier<T> T) {
        return T.get();
    }
    
    @Override
    public User save(User user) {
        UserEntity entity = mapper.toEntity(user);
        return mapper.toDomain(authJpaRepository.save(entity));
    }
    
    @Override
    public Optional<User> findByEmail(String email) {
        Optional<UserEntity> userEntity = authJpaRepository.findByEmail(email);
        return userEntity.map(mapper::toDomain);
    }
    
    @Override
    public boolean existsByEmail(String email) {
        return authJpaRepository.existsByEmail(email);
    }
}

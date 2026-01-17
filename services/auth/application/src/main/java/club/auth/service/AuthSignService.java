package club.auth.service;

import club.auth.domain.User;
import club.auth.domain.type.UserStatus;
import club.auth.exception.AuthException;
import club.auth.port.AuthCommandRepositoryPort;
import club.auth.port.PasswordEncoderPort;
import club.auth.readmodel.AuthCommandModels.LoginTokenModel;
import club.auth.readmodel.AuthCommandModels.SignInRequestModel;
import club.auth.readmodel.AuthCommandModels.SignUpRequestModel;
import club.auth.readmodel.AuthCommandModels.SignOutRequestModel;
import club.auth.usecase.sign.AuthEmailCheckUseCase;
import club.auth.usecase.sign.AuthSignOutUseCase;
import club.auth.usecase.token.AuthIssueTokenUseCase;
import club.auth.usecase.sign.AuthSignInUseCase;
import club.auth.usecase.sign.AuthSignUpUseCase;
import club.auth.usecase.token.AuthRevokeTokenUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static club.auth.exception.AuthErrorCode.AUTH_ACCOUNT_ALREADY_EXIST;
import static club.auth.exception.AuthErrorCode.AUTH_ACCOUNT_LOGIN_FAILED;

@Service
@RequiredArgsConstructor
public class AuthSignService implements AuthSignUpUseCase, AuthSignInUseCase, AuthEmailCheckUseCase, AuthSignOutUseCase {
    
    private final AuthIssueTokenUseCase authIssueTokenUseCase;
    
    private final AuthRevokeTokenUseCase authRevokeTokenUseCase;
    
    private final AuthCommandRepositoryPort commandRepository;
    
    private final PasswordEncoderPort passwordEncoder;
    
    /**
     * 이메일 중복 여부 확인
     *
     * @param email
     * @return boolean
     */
    @Override
    public boolean existsByEmail(String email) {
        return commandRepository.existsByEmail(email);
    }
    
    /**
     * 회원가입
     *
     * @param signUpRequest
     * @return LoginTokenModel
     */
    @Override
    public LoginTokenModel signUp(SignUpRequestModel signUpRequest) {
        var newUser = commandRepository.transaction(() -> {
            // 1. email 중복 체크
            boolean exists = commandRepository.existsByEmail(signUpRequest.email());
            
            if (exists) {
                throw new AuthException(AUTH_ACCOUNT_ALREADY_EXIST);
            }
            
            // 2. password 암호화
            String encodedPassword = passwordEncoder.encode(signUpRequest.password());
            
            // 3. user 도메인 객체 생성
            var user = User.builder()
                    .username(signUpRequest.username())
                    .email(signUpRequest.email())
                    .encodedPassword(encodedPassword)
                    .status(UserStatus.ACTIVE) // 기본 상태는 ACTIVE
                    .build();
            
            // 4. user 저장
            return commandRepository.save(user);
        });
        
        // 5. 사용자 회원 가입 성공 시, accessToken & refreshToken 발급
        return authIssueTokenUseCase.issueToken(newUser.getId());
    }
    
    /**
     * 로그인
     *
     * @param signInRequest
     * @return LoginTokenModel
     */
    @Override
    public LoginTokenModel signIn(SignInRequestModel signInRequest) {
        // 1. email 사용자 조회
        var user = commandRepository.findByEmail(signInRequest.email())
                .orElseThrow(() -> new AuthException(AUTH_ACCOUNT_LOGIN_FAILED));
        
        // 2. 비밀번호 검증
        if (!passwordEncoder.matches(signInRequest.password(), user.getEncodedPassword())) {
            throw new AuthException(AUTH_ACCOUNT_LOGIN_FAILED);
        }
        
        // 3. 사용자 인증 성공 시, accessToken & refreshToken 발급
        return authIssueTokenUseCase.issueToken(user.getId());
    }
    
    /**
     * 로그아웃
     *
     * @param signOutRequestModel
     */
    @Override
    public void logout(SignOutRequestModel signOutRequestModel) {
        authRevokeTokenUseCase.revokeToken(signOutRequestModel.userId(), signOutRequestModel.refreshToken());
    }
}

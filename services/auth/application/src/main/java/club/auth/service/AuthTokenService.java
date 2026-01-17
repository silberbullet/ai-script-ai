package club.auth.service;

import club.auth.exception.AuthException;
import club.auth.port.AuthRedisPort;
import club.auth.port.GenerateTokenPort;
import club.auth.readmodel.AuthCommandModels.LoginTokenModel;
import club.auth.usecase.token.AuthIssueTokenUseCase;
import club.auth.usecase.token.AuthRefreshTokenUseCase;
import club.auth.usecase.token.AuthRevokeTokenUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;

import static club.auth.exception.AuthErrorCode.AUTH_REFRESH_TOKEN_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class AuthTokenService implements AuthIssueTokenUseCase, AuthRefreshTokenUseCase, AuthRevokeTokenUseCase {
    
    private final AuthRedisPort authRedisPort;
    
    private final GenerateTokenPort generateTokenPort;
    
    // TODO: Properties 로 대체
    private static final int ACCESS_TOKEN_EXPIRATION = 600;             // accessToken 유효 기간 (10분)
    private static final int REFRESH_TOKEN_EXPIRATION = 30;             // refreshToken 유효 기간 (30일)
    // private static final int OTP_EXPIRATION = 5;                        // otp 유효 기간 (5분)
    //  private static final int OTP_LENGTH = 6;                            // otp 길이
    // private static final int EMAIL_VERIFICATION_TOKEN_EXPIRATION = 10;  // 이메일 인증 클라이언트 검증 토큰 유효 기간 (10분)
    // private static final int PASSWORD_RESET_URL_EXPIRATION = 60;        // 비밀번호 재설정 링크 유효 기간 (60분)
    // private static final String PASSWORD_RESET_URL = "";                // 비밀번호 재설정 링크, TODO: 실제 도메인으로 변경 필요
    
    @Override
    public LoginTokenModel issueToken(String userId) {
        // accessToken 발급
        String accessToken = generateTokenPort.generateAccessToken(userId, ACCESS_TOKEN_EXPIRATION);
        
        // refreshToken 발급
        String refreshToken = generateTokenPort.generateSecureRandom();
        String hashedRefreshToken = generateTokenPort.hashSha256(refreshToken);
        
        // refreshToken 저장
        String hashedRefreshTokenKey = userId + ":" + hashedRefreshToken;
        authRedisPort.save(hashedRefreshTokenKey, hashedRefreshToken, Duration.ofDays(REFRESH_TOKEN_EXPIRATION));
        
        // 사용자별 토큰 관리를 위해 해시값을 Set에 저장
        String userSetKey = "user_tokens:" + userId;
        authRedisPort.addToSet(userSetKey, hashedRefreshToken);
        
        return LoginTokenModel.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
    
    @Override
    public LoginTokenModel refreshLoginToken(String userId, String refreshToken) {
        String hashedRefreshToken = generateTokenPort.hashSha256(refreshToken);
        String hashedRefreshTokenKey = userId + ":" + hashedRefreshToken;
        
        if (!authRedisPort.hasKey(hashedRefreshTokenKey)) {
            throw new AuthException(AUTH_REFRESH_TOKEN_NOT_FOUND);
        }
        
        // 기존 refreshToken 삭제
        authRedisPort.delete(hashedRefreshTokenKey);
        
        String userSetKey = "user_tokens:" + userId;
        authRedisPort.removeFromSet(userSetKey, hashedRefreshToken);
        
        // 새로운 accessToken, refreshToken 발급
        return issueToken(userId);
    }
    
    @Override
    public void revokeToken(String userId, String refreshToken) {
        // refreshToken 삭제
        String hashedRefreshToken = generateTokenPort.hashSha256(refreshToken);
        String hashedRefreshTokenKey = userId + ":" + hashedRefreshToken;
        authRedisPort.delete(hashedRefreshTokenKey);
        
        // 사용자별 토큰 관리 Set에서 해당 refreshToken 해시값 삭제
        String userSetKey = "user_tokens:" + userId;
        authRedisPort.removeFromSet(userSetKey, hashedRefreshToken);
    }
}

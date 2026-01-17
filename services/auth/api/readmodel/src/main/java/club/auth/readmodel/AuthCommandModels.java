package club.auth.readmodel;

import lombok.Builder;

public final class AuthCommandModels {

    public record SignUpRequestModel(
            String username,
            String email,
            String password

//            boolean agreedTerms,    // 이용약관 동의
//            boolean agreedPrivacy   // 개인정보처리방침 동의
    ) {
    }
    
    public record SignInRequestModel(
            String email,
            String password
    ) {
    }
    
    public record SignOutRequestModel(
            String userId,
            String refreshToken
    ) {
    }
    
    @Builder
    public record LoginTokenModel(
            String accessToken,
            String refreshToken
    ) {
    }
}

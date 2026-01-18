package club.auth.web;

import club.auth.readmodel.AuthCommandModels.LoginModel;
import club.auth.readmodel.AuthCommandModels.SignOutRequestModel;
import club.auth.readmodel.AuthCommandModels.LoginTokenModel;
import club.auth.readmodel.AuthCommandModels.SignInRequestModel;
import club.auth.readmodel.AuthCommandModels.SignUpRequestModel;
import club.auth.usecase.sign.AuthSignInUseCase;
import club.auth.usecase.sign.AuthSignOutUseCase;
import club.auth.usecase.sign.AuthSignUpUseCase;
import club.auth.usecase.token.AuthRefreshTokenUseCase;
import club.auth.web.cookie.util.CookieUtil;
import club.auth.web.dto.AuthCommandDto.LoginRequest;
import club.auth.web.dto.AuthCommandDto.LoginResponse;
import club.auth.web.dto.AuthCommandDto.SignUpRequest;
import club.auth.web.mapper.AuthDtoMapper;
import club.jwt.filter.annotation.AuthUser;
import club.jwt.filter.annotation.AuthorizedUser;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Auth", description = "인증 관련 API")
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthCommandApi {
    
    private final AuthSignUpUseCase authSignUpUseCase;
    private final AuthSignInUseCase authSignInUseCase;
    private final AuthSignOutUseCase authSignOutUseCase;
    private final AuthRefreshTokenUseCase authRefreshTokenUseCase;
    private final AuthDtoMapper mapper;
    private final CookieUtil cookieUtil;
    
    @PostMapping("/signup")
    @Operation(
            summary = "회원가입",
            description = "일반 사용자를 등록합니다."
    )
    public ResponseEntity<LoginResponse> signUp(@RequestBody SignUpRequest signUpRequest) {
        // 회원 가입 로직 구현
        SignUpRequestModel requestModel = mapper.toModel(signUpRequest);
        LoginModel responseModel = authSignUpUseCase.signUp(requestModel);
        LoginResponse result = mapper.toDto(responseModel, responseModel.loginTokenModel().accessToken());
        
        ResponseCookie refreshTokenCookie = cookieUtil.createRefreshTokenCookie(responseModel.loginTokenModel().refreshToken());
        
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(result);
    }
    
    @PostMapping("/login")
    @Operation(
            summary = "로그인",
            description = """
                        일반 사용자가 로그인합니다.
                        리프레시 토큰(refreshToken)은 HttpOnly 쿠키로 반환합니다.
                    """
    )
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        // 로그인 로직 구현
        SignInRequestModel requestModel = mapper.toModel(loginRequest);
        LoginModel responseModel = authSignInUseCase.signIn(requestModel);
        LoginResponse result = mapper.toDto(responseModel, responseModel.loginTokenModel().accessToken());
        
        ResponseCookie refreshTokenCookie = cookieUtil.createRefreshTokenCookie(responseModel.loginTokenModel().refreshToken());
        
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(result);
    }
    
    @PostMapping("/logout")
    @Operation(summary = "로그아웃", description = "사용자가 로그아웃합니다.")
    public ResponseEntity<Void> logout(@AuthUser AuthorizedUser authorizedUser,
                                       @CookieValue("refreshToken") String refreshToken) {
        SignOutRequestModel requestModel = mapper.toModel(authorizedUser.userId(), refreshToken);
        
        authSignOutUseCase.logout(requestModel);
        
        ResponseCookie refreshTokenCookie = cookieUtil.deleteRefreshTokenCookie();
        
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .build();
    }
    
    @PostMapping("/token/refresh")
    @Operation(
            summary = "액세스 토큰 재발급",
            description = "리프레시 토큰(HttpOnly 쿠키)을 이용해 새로운 액세스 토큰을 발급받습니다."
    )
    public ResponseEntity<LoginResponse> refreshAccessToken(@AuthUser AuthorizedUser authorizedUser,
                                                            @CookieValue("refreshToken") String refreshToken) {
        LoginTokenModel responseModel = authRefreshTokenUseCase.refreshLoginToken(authorizedUser.userId(), refreshToken);
        
        LoginResponse result = mapper.toDto(responseModel);
        
        ResponseCookie refreshTokenCookie = cookieUtil.createRefreshTokenCookie(responseModel.refreshToken());
        
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(result);
    }
}

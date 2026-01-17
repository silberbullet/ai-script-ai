package club.auth.usecase.token;

import club.auth.readmodel.AuthCommandModels.LoginTokenModel;

public interface AuthRefreshTokenUseCase {
    
    LoginTokenModel refreshLoginToken(String userId, String refreshToken);
}

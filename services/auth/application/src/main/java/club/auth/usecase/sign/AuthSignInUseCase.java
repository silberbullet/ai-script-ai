package club.auth.usecase.sign;

import club.auth.readmodel.AuthCommandModels.LoginTokenModel;
import club.auth.readmodel.AuthCommandModels.SignInRequestModel;

public interface AuthSignInUseCase {
    
    LoginTokenModel signIn(SignInRequestModel signInRequest);
}

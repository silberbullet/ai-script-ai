package club.auth.usecase.sign;

import club.auth.readmodel.AuthCommandModels.LoginModel;
import club.auth.readmodel.AuthCommandModels.SignInRequestModel;

public interface AuthSignInUseCase {
    
    LoginModel signIn(SignInRequestModel signInRequest);
}

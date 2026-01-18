package club.auth.usecase.sign;

import club.auth.readmodel.AuthCommandModels.LoginModel;
import club.auth.readmodel.AuthCommandModels.SignUpRequestModel;

public interface AuthSignUpUseCase {

    LoginModel signUp(SignUpRequestModel signUpRequest);
}

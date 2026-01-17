package club.auth.usecase.sign;

import club.auth.readmodel.AuthCommandModels.LoginTokenModel;
import club.auth.readmodel.AuthCommandModels.SignUpRequestModel;

public interface AuthSignUpUseCase {

    LoginTokenModel signUp(SignUpRequestModel signUpRequest);
}

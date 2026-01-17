package club.auth.usecase.sign;

import club.auth.readmodel.AuthCommandModels.SignOutRequestModel;

public interface AuthSignOutUseCase {
    
    void logout(SignOutRequestModel signOutRequestModel) ;
}

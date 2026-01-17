package club.auth.usecase.token;

import club.auth.readmodel.AuthCommandModels.LoginTokenModel;

public interface AuthIssueTokenUseCase {
    
    LoginTokenModel issueToken(String userId);
}

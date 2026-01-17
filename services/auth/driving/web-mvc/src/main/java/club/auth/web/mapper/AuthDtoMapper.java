package club.auth.web.mapper;

import club.auth.domain.User;
import club.auth.readmodel.AuthCommandModels.LoginTokenModel;
import club.auth.readmodel.AuthCommandModels.SignInRequestModel;
import club.auth.readmodel.AuthCommandModels.SignOutRequestModel;
import club.auth.readmodel.AuthCommandModels.SignUpRequestModel;
import club.auth.web.dto.AuthCommandDto.LoginRequest;
import club.auth.web.dto.AuthCommandDto.LoginResponse;
import club.auth.web.dto.AuthCommandDto.SignUpRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuthDtoMapper {
    
    User toDomain(SignUpRequest dto);
    
    SignInRequestModel toModel(LoginRequest loginRequest);
    
    SignUpRequestModel toModel(SignUpRequest signUpRequest);
    
    SignOutRequestModel toModel(String userId, String refreshToken);
    
    LoginResponse toDto(LoginTokenModel signUpRequestModel);
}

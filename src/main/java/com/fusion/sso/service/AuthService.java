package com.fusion.sso.service;

import com.fusion.sso.dto.*;
import com.fusion.sso.exception.CommonNotFound;
import io.fusionauth.domain.api.LoginResponse;
import io.fusionauth.domain.api.UserResponse;
import io.fusionauth.domain.api.jwt.ValidateResponse;
import io.fusionauth.domain.api.user.ForgotPasswordResponse;

import javax.servlet.http.HttpServletRequest;

public interface AuthService {

	public UserResponse createUser(CreateUser request, HttpServletRequest httpServletRequest) throws CommonNotFound;

	public UserResponse updateUser(UpdateUser request, HttpServletRequest httpServletRequest) throws CommonNotFound;

	public LoginResponse login(Login request, HttpServletRequest httpServletRequest) throws CommonNotFound;

	public UserResponse getUserById(String userId, HttpServletRequest httpServletRequest) throws CommonNotFound;

	public void changePassword(ChangePassword request, HttpServletRequest httpServletRequest) throws CommonNotFound;

	public ForgotPasswordResponse forgotPassword(ForgotPassword request, HttpServletRequest httpServletRequest)
			throws CommonNotFound;

	public void deactivateUserById(ActivateDeactivate request, HttpServletRequest httpServletRequest)
			throws CommonNotFound;

	public UserResponse reactivateUserById(ActivateDeactivate request, HttpServletRequest httpServletRequest)
			throws CommonNotFound;

	public UserResponse getUserByEmail(String email, HttpServletRequest httpServletRequest) throws CommonNotFound;

	public ValidateResponse validateJwt(ValidateJWT request, HttpServletRequest httpServletRequest)
			throws CommonNotFound;

}

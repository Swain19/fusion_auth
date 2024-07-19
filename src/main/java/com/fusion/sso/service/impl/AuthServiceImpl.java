package com.fusion.sso.service.impl;

import com.fusion.sso.common.FusionAuthUtils;
import com.fusion.sso.common.Utils;
import com.fusion.sso.dto.*;
import com.fusion.sso.exception.CommonNotFound;
import com.fusion.sso.service.AuthService;
import com.inversoft.error.Errors;
import com.inversoft.rest.ClientResponse;
import io.fusionauth.client.FusionAuthClient;
import io.fusionauth.domain.User;
import io.fusionauth.domain.api.LoginRequest;
import io.fusionauth.domain.api.LoginResponse;
import io.fusionauth.domain.api.UserRequest;
import io.fusionauth.domain.api.UserResponse;
import io.fusionauth.domain.api.jwt.ValidateResponse;
import io.fusionauth.domain.api.user.ChangePasswordRequest;
import io.fusionauth.domain.api.user.ForgotPasswordRequest;
import io.fusionauth.domain.api.user.ForgotPasswordResponse;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Log4j2
public class AuthServiceImpl implements AuthService {

	@Autowired
	private Utils utils;

	@Autowired
	private FusionAuthClient fusionAuthClient;

	@Autowired
	private FusionAuthUtils fusionAuthUtils;

	@Override
	public UserResponse createUser(CreateUser request, HttpServletRequest httpServletRequest) throws CommonNotFound {
		log.info("Create User Request Start: {}", request.toJson());

		UUID tenantId = UUID.fromString(fusionAuthUtils.getTenantId());

		User user = new User();
		user.firstName = request.getFirstName();
		user.lastName = request.getLastName();
		user.middleName = request.getMiddleName();
		user.fullName = request.getFullName();
		user.email = request.getEmail();
		user.password = request.getPassword();
		user.username = request.getEmail();
		user.active = true;
		user.mobilePhone = request.getMobilePhone();
		user.birthDate = request.getDob();
		user.passwordChangeRequired = false;

		UserRequest userreq = new UserRequest();
		userreq.applicationId = UUID.fromString(fusionAuthUtils.getApplicationId());
		userreq.sendSetPasswordEmail = false;
		userreq.skipVerification = false;
		userreq.disableDomainBlock = false;
		userreq.user = user;

		ClientResponse<UserResponse, Errors> response = fusionAuthClient.setTenantId(tenantId).createUser(null,
				userreq);

		log.info("User Create Response Success: {}", response.getSuccessResponse());
		log.info("User Create Response Error: {}", response.getErrorResponse());

		Integer status = response.status;
		String path = httpServletRequest.getRequestURI();

		if (status == 200) {
			return response.getSuccessResponse();
		} else if (status == 400) {
			String errorMessage = utils.mapFusionAuthError(response.getErrorResponse());
			throw new CommonNotFound(errorMessage, path, HttpStatus.BAD_REQUEST);
		} else if (status == 401) {
			throw new CommonNotFound("You did not supply a valid Authorization header. ", path,
					HttpStatus.UNAUTHORIZED);
		} else if (status == 500) {
			throw new CommonNotFound("There was an internal error. ", path, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (status == 503) {
			throw new CommonNotFound(
					"The search index is not available or encountered an exception so the request cannot be completed.",
					path, HttpStatus.SERVICE_UNAVAILABLE);
		} else if (status == 504) {
			throw new CommonNotFound("One or more Webhook endpoints returned an invalid response or were unreachable.",
					path, HttpStatus.GATEWAY_TIMEOUT);
		} else {
			throw new CommonNotFound("Something went wrong!", path, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public UserResponse updateUser(UpdateUser request, HttpServletRequest httpServletRequest) throws CommonNotFound {
		log.info("Update User Request Start: {}", request.toJson());

		UUID userId = UUID.fromString(request.getUserId());

		User user = new User();
		user.firstName = request.getFirstName();
		user.lastName = request.getLastName();
		user.middleName = request.getMiddleName();
		user.fullName = request.getFullName();
		user.email = request.getEmail();
		user.password = request.getPassword();
		user.username = request.getEmail();
		user.active = true;
		user.mobilePhone = request.getMobilePhone();
		user.birthDate = request.getDob();
		user.passwordChangeRequired = false;

		UserRequest userreq = new UserRequest();
		userreq.applicationId = UUID.fromString(fusionAuthUtils.getApplicationId());
		userreq.sendSetPasswordEmail = false;
		userreq.skipVerification = false;
		userreq.disableDomainBlock = false;
		userreq.user = user;

		ClientResponse<UserResponse, Errors> response = fusionAuthClient.updateUser(userId, userreq);

		log.info("User Update Response Success: {}", response.getSuccessResponse());
		log.info("User Update Response Error: {}", response.getErrorResponse());

		Integer status = response.status;
		String path = httpServletRequest.getRequestURI();

		if (status == 200) {
			return response.getSuccessResponse();
		} else if (status == 400) {
			String errorMessage = utils.mapFusionAuthError(response.getErrorResponse());
			throw new CommonNotFound(errorMessage, path, HttpStatus.BAD_REQUEST);
		} else if (status == 401) {
			throw new CommonNotFound("You did not supply a valid Authorization header. ", path,
					HttpStatus.UNAUTHORIZED);
		} else if (status == 404) {
			throw new CommonNotFound("The object you are trying to update doesn’t exist.", path, HttpStatus.NOT_FOUND);
		} else if (status == 500) {
			throw new CommonNotFound("There was an internal error. ", path, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (status == 503) {
			throw new CommonNotFound(
					"The search index is not available or encountered an exception so the request cannot be completed.",
					path, HttpStatus.SERVICE_UNAVAILABLE);
		} else if (status == 504) {
			throw new CommonNotFound("One or more Webhook endpoints returned an invalid response or were unreachable.",
					path, HttpStatus.GATEWAY_TIMEOUT);
		} else {
			throw new CommonNotFound("Something went wrong!", path, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public LoginResponse login(Login request, HttpServletRequest httpServletRequest) throws CommonNotFound {

		LoginRequest loginRequest = new LoginRequest();
		loginRequest.loginId = request.getEmail();
		loginRequest.password = request.getPassword();
		loginRequest.applicationId = UUID.fromString(fusionAuthUtils.getApplicationId());

		ClientResponse<LoginResponse, Errors> response = fusionAuthClient.login(loginRequest);

		log.info("Login Response Success: {}", response.getSuccessResponse());
		log.info("Login Response Error: {}", response.getErrorResponse());
		log.info("Login Response Status Value: {}", response.status);

		Integer status = response.status;
		String path = httpServletRequest.getRequestURI();

		if (status == 200) {
			return response.getSuccessResponse();
		} else if (status == 202) {
			return response.getSuccessResponse();
		} else if (status == 203) {
			return response.getSuccessResponse();
		} else if (status == 212) {
			return response.getSuccessResponse();
		} else if (status == 213) {
			return response.getSuccessResponse();
		} else if (status == 242) {
			return response.getSuccessResponse();
		} else if (status == 400) {
			if (response.getErrorResponse() == null) {
				throw new CommonNotFound("Email or Password is not correct", path, HttpStatus.BAD_REQUEST);
			} else {
				String errorMessage = utils.mapFusionAuthError(response.getErrorResponse());
				throw new CommonNotFound(errorMessage, path, HttpStatus.BAD_REQUEST);
			}
		} else if (status == 401) {
			if (response.getErrorResponse() == null) {
				throw new CommonNotFound("Email or Password is not correct", path, HttpStatus.UNAUTHORIZED);
			} else {
				String errorMessage = utils.mapFusionAuthError(response.getErrorResponse());
				throw new CommonNotFound(errorMessage, path, HttpStatus.UNAUTHORIZED);
			}
		} else if (status == 404) {
			throw new CommonNotFound("The user was not found or the password was incorrect.", path,
					HttpStatus.NOT_FOUND);
		} else if (status == 409) {
			throw new CommonNotFound("The user is currently in an action that has prevented login.", path,
					HttpStatus.CONFLICT);
		} else if (status == 410) {
			throw new CommonNotFound("The user has expired.", path, HttpStatus.GONE);
		} else if (status == 423) {
			throw new CommonNotFound("The user is locked and cannot login.", path, HttpStatus.LOCKED);
		} else if (status == 500) {
			throw new CommonNotFound("There was an internal error.", path, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (status == 503) {
			if (response.getErrorResponse() == null) {
				throw new CommonNotFound(
						"The search index is not available or encountered an exception so the request cannot be completed.",
						path, HttpStatus.SERVICE_UNAVAILABLE);
			} else {
				String errorMessage = utils.mapFusionAuthError(response.getErrorResponse());
				throw new CommonNotFound(errorMessage, path, HttpStatus.SERVICE_UNAVAILABLE);
			}
		} else {
			throw new CommonNotFound("Something went wrong!", path, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public UserResponse getUserById(String userId, HttpServletRequest httpServletRequest) throws CommonNotFound {

		ClientResponse<UserResponse, Errors> response = fusionAuthClient.retrieveUser(UUID.fromString(userId));

		log.info("Get User By UserId Response Success: {}", response.getSuccessResponse());
		log.info("Get User By UserId Response Error: {}", response.getErrorResponse());

		Integer status = response.status;
		String path = httpServletRequest.getRequestURI();

		if (status == 200) {
			return response.getSuccessResponse();
		} else if (status == 400) {
			String errorMessage = utils.mapFusionAuthError(response.getErrorResponse());
			throw new CommonNotFound(errorMessage, path, HttpStatus.BAD_REQUEST);
		} else if (status == 401) {
			throw new CommonNotFound("You did not supply a valid Authorization header. ", path,
					HttpStatus.UNAUTHORIZED);
		} else if (status == 500) {
			throw new CommonNotFound("There was an internal error. ", path, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (status == 503) {
			throw new CommonNotFound(
					"The search index is not available or encountered an exception so the request cannot be completed.",
					path, HttpStatus.SERVICE_UNAVAILABLE);
		} else if (status == 404) {
			throw new CommonNotFound("The object you requested doesn’t exist.", path, HttpStatus.NOT_FOUND);
		} else {
			throw new CommonNotFound("Something went wrong!", path, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public UserResponse getUserByEmail(String email, HttpServletRequest httpServletRequest) throws CommonNotFound {

		UUID tenantId = UUID.fromString(fusionAuthUtils.getTenantId());
		ClientResponse<UserResponse, Errors> response = fusionAuthClient.setTenantId(tenantId)
				.retrieveUserByEmail(email);

		log.info("Get User By Email Response Success: {}", response.getSuccessResponse());
		log.info("Get User By Email Response Error: {}", response.getErrorResponse());

		Integer status = response.status;
		String path = httpServletRequest.getRequestURI();

		if (status == 200) {
			return response.getSuccessResponse();
		} else if (status == 400) {
			String errorMessage = utils.mapFusionAuthError(response.getErrorResponse());
			throw new CommonNotFound(errorMessage, path, HttpStatus.BAD_REQUEST);
		} else if (status == 401) {
			throw new CommonNotFound("You did not supply a valid Authorization header. ", path,
					HttpStatus.UNAUTHORIZED);
		} else if (status == 500) {
			throw new CommonNotFound("There was an internal error. ", path, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (status == 503) {
			throw new CommonNotFound(
					"The search index is not available or encountered an exception so the request cannot be completed.",
					path, HttpStatus.SERVICE_UNAVAILABLE);
		} else if (status == 404) {
			throw new CommonNotFound("The object you requested doesn’t exist.", path, HttpStatus.BAD_REQUEST);
		} else {
			throw new CommonNotFound("Something went wrong!", path, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public void changePassword(ChangePassword request, HttpServletRequest httpServletRequest) throws CommonNotFound {
		ChangePasswordRequest pwdreq = new ChangePasswordRequest();

		pwdreq.applicationId = UUID.fromString(fusionAuthUtils.getApplicationId());
		pwdreq.changePasswordId = request.getChangePasswordId();
		pwdreq.currentPassword = request.getCurrentPassword();
		pwdreq.loginId = request.getEmail();
		pwdreq.password = request.getPassword();

		ClientResponse<Void, Errors> response = fusionAuthClient.changePasswordByIdentity(pwdreq);

		log.info("Change Password Response Success: {}", response.getSuccessResponse());
		log.info("Change Password Response Error: {}", response.getErrorResponse());

		Integer status = response.status;
		String path = httpServletRequest.getRequestURI();

		if (status == 200) {

		} else if (status == 400) {
			String errorMessage = utils.mapFusionAuthError(response.getErrorResponse());
			throw new CommonNotFound(errorMessage, path, HttpStatus.BAD_REQUEST);
		} else if (status == 401) {
			throw new CommonNotFound("You did not supply a valid Authorization header. ", path,
					HttpStatus.UNAUTHORIZED);
		} else if (status == 403) {
			throw new CommonNotFound("The forgot password functionality has been disabled.", path,
					HttpStatus.FORBIDDEN);
		} else if (status == 404) {
			throw new CommonNotFound("The User could not be found.", path, HttpStatus.NOT_FOUND);
		} else if (status == 500) {
			throw new CommonNotFound("There was an internal error. ", path, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (status == 503) {
			String errorMessage = utils.mapFusionAuthError(response.getErrorResponse());
			throw new CommonNotFound(errorMessage, path, HttpStatus.SERVICE_UNAVAILABLE);
		} else {
			throw new CommonNotFound("Something went wrong!", path, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ForgotPasswordResponse forgotPassword(ForgotPassword request, HttpServletRequest httpServletRequest)
			throws CommonNotFound {
		ForgotPasswordRequest pwdreq = new ForgotPasswordRequest();

		pwdreq.applicationId = UUID.fromString(fusionAuthUtils.getApplicationId());
		pwdreq.loginId = request.getEmail();
		pwdreq.sendForgotPasswordEmail = false;

		ClientResponse<ForgotPasswordResponse, Errors> response = fusionAuthClient.forgotPassword(pwdreq);

		log.info("Forgot Password Response Success: {}", response.getSuccessResponse());
		log.info("Forgot Password Response Error: {}", response.getErrorResponse());

		Integer status = response.status;
		String path = httpServletRequest.getRequestURI();

		if (status == 200) {
			return response.getSuccessResponse();
		} else if (status == 400) {
			String errorMessage = utils.mapFusionAuthError(response.getErrorResponse());
			throw new CommonNotFound(errorMessage, path, HttpStatus.BAD_REQUEST);
		} else if (status == 401) {
			throw new CommonNotFound("You did not supply a valid Authorization header. ", path,
					HttpStatus.UNAUTHORIZED);
		} else if (status == 403) {
			throw new CommonNotFound("The forgot password functionality has been disabled.", path,
					HttpStatus.FORBIDDEN);
		} else if (status == 404) {
			throw new CommonNotFound("The User could not be found.", path, HttpStatus.NOT_FOUND);
		} else if (status == 500) {
			throw new CommonNotFound("There was an internal error. ", path, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (status == 503) {
			String errorMessage = utils.mapFusionAuthError(response.getErrorResponse());
			throw new CommonNotFound(errorMessage, path, HttpStatus.SERVICE_UNAVAILABLE);
		} else if (status == 422) {
			throw new CommonNotFound(
					"The User does not have an email address, this request cannot be completed. Before attempting the request again add an email address to the user.",
					path, HttpStatus.UNPROCESSABLE_ENTITY);
		} else {
			throw new CommonNotFound("Something went wrong!", path, HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public void deactivateUserById(ActivateDeactivate request, HttpServletRequest httpServletRequest)
			throws CommonNotFound {

		ClientResponse<Void, Errors> response = fusionAuthClient.deactivateUser(UUID.fromString(request.getUserId()));

		log.info("Deactivate User Response Success: {}", response.getSuccessResponse());
		log.info("Deactivate User Response Error: {}", response.getErrorResponse());

		Integer status = response.status;
		String path = httpServletRequest.getRequestURI();

		if (status == 200) {

		} else if (status == 400) {
			String errorMessage = utils.mapFusionAuthError(response.getErrorResponse());
			throw new CommonNotFound(errorMessage, path, HttpStatus.BAD_REQUEST);
		} else if (status == 401) {
			throw new CommonNotFound("You did not supply a valid Authorization header. ", path,
					HttpStatus.UNAUTHORIZED);
		} else if (status == 404) {
			throw new CommonNotFound("The object you are trying to update doesn’t exist.", path,
					HttpStatus.BAD_REQUEST);
		} else if (status == 500) {
			throw new CommonNotFound("There was an internal error. ", path, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (status == 503) {
			throw new CommonNotFound(
					"The search index is not available or encountered an exception so the request cannot be completed.",
					path, HttpStatus.SERVICE_UNAVAILABLE);
		} else if (status == 504) {
			throw new CommonNotFound("One or more Webhook endpoints returned an invalid response or were unreachable.",
					path, HttpStatus.GATEWAY_TIMEOUT);
		} else {
			throw new CommonNotFound("Something went wrong!", path, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public UserResponse reactivateUserById(ActivateDeactivate request, HttpServletRequest httpServletRequest)
			throws CommonNotFound {

		ClientResponse<UserResponse, Errors> response = fusionAuthClient
				.reactivateUser(UUID.fromString(request.getUserId()));

		fusionAuthClient.validateJWT(null);

		log.info("Reactivate User Response Success: {}", response.getSuccessResponse());
		log.info("Reactivate User Response Error: {}", response.getErrorResponse());

		Integer status = response.status;
		String path = httpServletRequest.getRequestURI();

		if (status == 200) {
			return response.getSuccessResponse();
		} else if (status == 400) {
			String errorMessage = utils.mapFusionAuthError(response.getErrorResponse());
			throw new CommonNotFound(errorMessage, path, HttpStatus.BAD_REQUEST);
		} else if (status == 401) {
			throw new CommonNotFound("You did not supply a valid Authorization header. ", path,
					HttpStatus.UNAUTHORIZED);
		} else if (status == 404) {
			throw new CommonNotFound("The object you are trying to update doesn’t exist.", path,
					HttpStatus.BAD_REQUEST);
		} else if (status == 500) {
			throw new CommonNotFound("There was an internal error. ", path, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (status == 503) {
			throw new CommonNotFound(
					"The search index is not available or encountered an exception so the request cannot be completed.",
					path, HttpStatus.SERVICE_UNAVAILABLE);
		} else if (status == 504) {
			throw new CommonNotFound("One or more Webhook endpoints returned an invalid response or were unreachable.",
					path, HttpStatus.GATEWAY_TIMEOUT);
		} else {
			throw new CommonNotFound("Something went wrong!", path, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public ValidateResponse validateJwt(ValidateJWT request, HttpServletRequest httpServletRequest)
			throws CommonNotFound {

		ClientResponse<ValidateResponse, Void> response = fusionAuthClient.validateJWT(request.getToken());

		log.info("Validate JWT Response Success: {}", response.getSuccessResponse());

		Integer status = response.status;
		String path = httpServletRequest.getRequestURI();

		if (status == 200) {
			return response.getSuccessResponse();
		} else if (status == 401) {
			throw new CommonNotFound("The access token is not valid.", path, HttpStatus.UNAUTHORIZED);
		} else if (status == 500) {
			throw new CommonNotFound("There was an internal error. ", path, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			throw new CommonNotFound("Something went wrong!", path, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}

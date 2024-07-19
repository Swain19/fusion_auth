package com.fusion.sso.service.impl;

import com.fusion.sso.common.FusionAuthUtils;
import com.fusion.sso.common.Utils;
import com.fusion.sso.dto.*;
import com.fusion.sso.exception.CommonNotFound;
import com.fusion.sso.service.TwoFactorService;
import com.inversoft.error.Errors;
import com.inversoft.rest.ClientResponse;
import io.fusionauth.client.FusionAuthClient;
import io.fusionauth.domain.api.LoginResponse;
import io.fusionauth.domain.api.TwoFactorRecoveryCodeResponse;
import io.fusionauth.domain.api.TwoFactorRequest;
import io.fusionauth.domain.api.TwoFactorResponse;
import io.fusionauth.domain.api.twoFactor.SecretResponse;
import io.fusionauth.domain.api.twoFactor.TwoFactorLoginRequest;
import io.fusionauth.domain.api.twoFactor.TwoFactorSendRequest;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;



import java.util.UUID;

@Service
@Log4j2
public class TwoFactorServiceImpl implements TwoFactorService {

	@Autowired
	private FusionAuthClient fusionAuthClient;

	@Autowired
	private Utils utils;

	@Autowired
	private FusionAuthUtils fusionAuthUtils;

	@Override
	public SecretResponse getAuthenticatorSecret(HttpServletRequest httpServletRequest) throws CommonNotFound {

		ClientResponse<SecretResponse, Void> response = fusionAuthClient.generateTwoFactorSecret();

		Integer status = response.status;
		String path = httpServletRequest.getRequestURI();

		if (status == 200) {
			return response.getSuccessResponse();
		} else if (status == 401) {
			throw new CommonNotFound("You did not supply a valid Authorization header. ", path,
					HttpStatus.UNAUTHORIZED);
		} else if (status == 500) {
			throw new CommonNotFound("There was an internal error. ", path, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (status == 503) {
			throw new CommonNotFound(
					"The search index is not available or encountered an exception so the request cannot be completed.",
					path, HttpStatus.SERVICE_UNAVAILABLE);
		} else {
			throw new CommonNotFound("Something went wrong!", path, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public TwoFactorResponse enableTwoFactorForAuthenticator(
			TwoFactorAuthenticatorRequest twoFactorAuthenticatorRequest, HttpServletRequest httpServletRequest)
			throws CommonNotFound {
		log.info("Enable two-factor authenticator request: {}", twoFactorAuthenticatorRequest);

		// Set tenant id
		UUID tenantId = UUID.fromString(fusionAuthUtils.getTenantId());

		// Set user id
		UUID userId = UUID.fromString(twoFactorAuthenticatorRequest.getUserId());

		TwoFactorRequest request = new TwoFactorRequest();
		request.code = twoFactorAuthenticatorRequest.getCode();
		request.method = "authenticator";
		request.secret = twoFactorAuthenticatorRequest.getSecret();

		ClientResponse<TwoFactorResponse, Errors> response = fusionAuthClient.setTenantId(tenantId)
				.enableTwoFactor(userId, request);

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
			throw new CommonNotFound("The User does not exist.", path, HttpStatus.BAD_REQUEST);
		} else if (status == 421) {
			throw new CommonNotFound("The code request parameter is not valid.", path, HttpStatus.BAD_REQUEST);
		} else if (status == 500) {
			throw new CommonNotFound("There was an internal error. ", path, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (status == 503) {
			throw new CommonNotFound(
					"The search index is not available or encountered an exception so the request cannot be completed.",
					path, HttpStatus.SERVICE_UNAVAILABLE);
		} else {
			throw new CommonNotFound("Something went wrong!", path, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean sendTwoFactorCodeForEnableDisableEmail(TwoFactorEmailRequest twoFactorEmailRequest,
														  HttpServletRequest httpServletRequest) throws CommonNotFound {
		log.info("Enable two-factor email send code request: {}", twoFactorEmailRequest);

		// Set tenant id
		UUID tenantId = UUID.fromString(fusionAuthUtils.getTenantId());

		TwoFactorSendRequest request = new TwoFactorSendRequest();
		request.email = twoFactorEmailRequest.getEmail();
		request.method = "email";
		request.userId = UUID.fromString(twoFactorEmailRequest.getUserId());

		ClientResponse<Void, Errors> response = fusionAuthClient.setTenantId(tenantId)
				.sendTwoFactorCodeForEnableDisable(request);

		Integer status = response.status;
		String path = httpServletRequest.getRequestURI();

		if (status == 200) {
			return true;
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
		} else {
			throw new CommonNotFound("Something went wrong!", path, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public TwoFactorResponse enableTwoFactorForEmail(EnableTwoFactorEmailRequest enableTwoFactorEmailRequest,
													 HttpServletRequest httpServletRequest) throws CommonNotFound {
		log.info("Enable two-factor email request: {}", enableTwoFactorEmailRequest);

		// Set tenant id
		UUID tenantId = UUID.fromString(fusionAuthUtils.getTenantId());

		// Set user id
		UUID userId = UUID.fromString(enableTwoFactorEmailRequest.getUserId());

		TwoFactorRequest request = new TwoFactorRequest();
		request.code = enableTwoFactorEmailRequest.getCode();
		request.method = "email";
		request.email = enableTwoFactorEmailRequest.getEmail();

		ClientResponse<TwoFactorResponse, Errors> response = fusionAuthClient.setTenantId(tenantId)
				.enableTwoFactor(userId, request);

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
			throw new CommonNotFound("The User does not exist.", path, HttpStatus.BAD_REQUEST);
		} else if (status == 421) {
			throw new CommonNotFound("The code request parameter is not valid.", path, HttpStatus.BAD_REQUEST);
		} else if (status == 500) {
			throw new CommonNotFound("There was an internal error. ", path, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (status == 503) {
			throw new CommonNotFound(
					"The search index is not available or encountered an exception so the request cannot be completed.",
					path, HttpStatus.SERVICE_UNAVAILABLE);
		} else {
			throw new CommonNotFound("Something went wrong!", path, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean sendTwoFactorCodeForEnableDisableSms(TwoFactorSmsRequest twoFactorSmsRequest,
														HttpServletRequest httpServletRequest) throws CommonNotFound {
		log.info("Enable two-factor sms send code request: {}", twoFactorSmsRequest);

		// Set tenant id
		UUID tenantId = UUID.fromString(fusionAuthUtils.getTenantId());

		TwoFactorSendRequest request = new TwoFactorSendRequest();
		request.mobilePhone = twoFactorSmsRequest.getMobilePhone();
		request.method = "sms";
		request.userId = UUID.fromString(twoFactorSmsRequest.getUserId());

		ClientResponse<Void, Errors> response = fusionAuthClient.setTenantId(tenantId)
				.sendTwoFactorCodeForEnableDisable(request);

		Integer status = response.status;
		String path = httpServletRequest.getRequestURI();

		if (status == 200) {
			return true;
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
		} else {
			throw new CommonNotFound("Something went wrong!", path, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public TwoFactorResponse enableTwoFactorForSms(EnableTwoFactorSmsRequest enableTwoFactorSmsRequest,
												   HttpServletRequest httpServletRequest) throws CommonNotFound {
		log.info("Enable two-factor sms request: {}", enableTwoFactorSmsRequest);

		// Set tenant id
		UUID tenantId = UUID.fromString(fusionAuthUtils.getTenantId());

		// Set user id
		UUID userId = UUID.fromString(enableTwoFactorSmsRequest.getUserId());

		TwoFactorRequest request = new TwoFactorRequest();
		request.code = enableTwoFactorSmsRequest.getCode();
		request.method = "email";
		request.mobilePhone = enableTwoFactorSmsRequest.getMobilePhone();

		ClientResponse<TwoFactorResponse, Errors> response = fusionAuthClient.setTenantId(tenantId)
				.enableTwoFactor(userId, request);

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
			throw new CommonNotFound("The User does not exist.", path, HttpStatus.BAD_REQUEST);
		} else if (status == 421) {
			throw new CommonNotFound("The code request parameter is not valid.", path, HttpStatus.BAD_REQUEST);
		} else if (status == 500) {
			throw new CommonNotFound("There was an internal error. ", path, HttpStatus.INTERNAL_SERVER_ERROR);
		} else if (status == 503) {
			throw new CommonNotFound(
					"The search index is not available or encountered an exception so the request cannot be completed.",
					path, HttpStatus.SERVICE_UNAVAILABLE);
		} else {
			throw new CommonNotFound("Something went wrong!", path, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public TwoFactorRecoveryCodeResponse generateRecoveryCode(String fusionAuthUserId,
			HttpServletRequest httpServletRequest) throws CommonNotFound {
		log.info("Generate Recovery Code request: {}", fusionAuthUserId);

		// Set user id
		UUID userId = UUID.fromString(fusionAuthUserId);

		ClientResponse<TwoFactorRecoveryCodeResponse, Errors> response = fusionAuthClient
				.generateTwoFactorRecoveryCodes(userId);

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
		} else {
			throw new CommonNotFound("Something went wrong!", path, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public TwoFactorRecoveryCodeResponse retrieveRecoveryCode(String fusionAuthUserId,
			HttpServletRequest httpServletRequest) throws CommonNotFound {
		log.info("Generate Recovery Code request: {}", fusionAuthUserId);

		// Set user id
		UUID userId = UUID.fromString(fusionAuthUserId);

		ClientResponse<TwoFactorRecoveryCodeResponse, Errors> response = fusionAuthClient
				.retrieveTwoFactorRecoveryCodes(userId);

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
		} else {
			throw new CommonNotFound("Something went wrong!", path, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public Boolean sendTwoFactorCodeForLoginUsingMethod(TwoFactorSendCodeRequest twoFactorSendCodeRequest,
			HttpServletRequest httpServletRequest) throws CommonNotFound {
		log.info("Send Two-Factor Code request: {}", twoFactorSendCodeRequest);

		TwoFactorSendRequest request = new TwoFactorSendRequest();
		request.methodId = twoFactorSendCodeRequest.getMethodId();

		ClientResponse<Void, Errors> response = fusionAuthClient
				.sendTwoFactorCodeForLoginUsingMethod(twoFactorSendCodeRequest.getTwoFactorId(), request);

		Integer status = response.status;
		String path = httpServletRequest.getRequestURI();

		if (status == 200) {
			return true;
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
		} else {
			throw new CommonNotFound("Something went wrong!", path, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Override
	public LoginResponse loginTwoFactor(TwoFactorLogin twoFactorLogin, HttpServletRequest httpServletRequest)
			throws CommonNotFound {
		log.info("Login Two-Factor request: {}", twoFactorLogin);

		TwoFactorLoginRequest request = new TwoFactorLoginRequest();
		request.applicationId = UUID.fromString(fusionAuthUtils.getApplicationId());
		request.code = twoFactorLogin.getCode();
		request.twoFactorId = twoFactorLogin.getTwoFactorId();

		ClientResponse<LoginResponse, Errors> response = fusionAuthClient.twoFactorLogin(request);

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
		} else if (status == 400) {
			if (response.getErrorResponse() == null) {
				throw new CommonNotFound("The request was invalid and/or malformed", path, HttpStatus.BAD_REQUEST);
			} else {
				String errorMessage = utils.mapFusionAuthError(response.getErrorResponse());
				throw new CommonNotFound(errorMessage, path, HttpStatus.BAD_REQUEST);
			}
		} else if (status == 404) {
			throw new CommonNotFound(
					"The twoFactorId was invalid or expired. You will need to authenticate again using the Login.",
					path, HttpStatus.NOT_FOUND);
		} else if (status == 409) {
			throw new CommonNotFound("The user is currently in an action that has prevented login.", path,
					HttpStatus.CONFLICT);
		} else if (status == 410) {
			throw new CommonNotFound("The user has expired.", path, HttpStatus.GONE);
		} else if (status == 421) {
			throw new CommonNotFound("The code request parameter is not valid.", path, HttpStatus.BAD_REQUEST);
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
}

package com.fusion.sso.service;

import com.fusion.sso.dto.*;
import com.fusion.sso.exception.CommonNotFound;
import io.fusionauth.domain.api.LoginResponse;
import io.fusionauth.domain.api.TwoFactorRecoveryCodeResponse;
import io.fusionauth.domain.api.TwoFactorResponse;
import io.fusionauth.domain.api.twoFactor.SecretResponse;
import javax.servlet.http.HttpServletRequest;


public interface TwoFactorService {

	public SecretResponse getAuthenticatorSecret(HttpServletRequest httpServletRequest) throws CommonNotFound;

	public TwoFactorResponse enableTwoFactorForAuthenticator(
			TwoFactorAuthenticatorRequest twoFactorAuthenticatorRequest, HttpServletRequest httpServletRequest)
			throws CommonNotFound;

	public Boolean sendTwoFactorCodeForEnableDisableEmail(TwoFactorEmailRequest twoFactorEmailRequest,
														  HttpServletRequest httpServletRequest) throws CommonNotFound;

	public TwoFactorResponse enableTwoFactorForEmail(EnableTwoFactorEmailRequest enableTwoFactorEmailRequest,
													 HttpServletRequest httpServletRequest) throws CommonNotFound;

	public Boolean sendTwoFactorCodeForEnableDisableSms(TwoFactorSmsRequest twoFactorSmsRequest,
														HttpServletRequest httpServletRequest) throws CommonNotFound;

	public TwoFactorResponse enableTwoFactorForSms(EnableTwoFactorSmsRequest enableTwoFactorSmsRequest,
												   HttpServletRequest httpServletRequest) throws CommonNotFound;

	public TwoFactorRecoveryCodeResponse generateRecoveryCode(String fusionAuthUserId,
			HttpServletRequest httpServletRequest) throws CommonNotFound;

	public TwoFactorRecoveryCodeResponse retrieveRecoveryCode(String fusionAuthUserId,
			HttpServletRequest httpServletRequest) throws CommonNotFound;

	public Boolean sendTwoFactorCodeForLoginUsingMethod(TwoFactorSendCodeRequest twoFactorSendCodeRequest,
			HttpServletRequest httpServletRequest) throws CommonNotFound;

	public LoginResponse loginTwoFactor(TwoFactorLogin twoFactorLogin, HttpServletRequest httpServletRequest)
			throws CommonNotFound;
}

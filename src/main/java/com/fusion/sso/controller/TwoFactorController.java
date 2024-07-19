package com.fusion.sso.controller;

import com.fusion.sso.dto.*;
import com.fusion.sso.exception.CommonNotFound;
import com.fusion.sso.service.TwoFactorService;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import io.fusionauth.domain.api.LoginResponse;
import io.fusionauth.domain.api.TwoFactorRecoveryCodeResponse;
import io.fusionauth.domain.api.TwoFactorResponse;
import io.fusionauth.domain.api.twoFactor.SecretResponse;
import io.swagger.annotations.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Base64;

@RestController
@RequestMapping("/auth")
@Slf4j
@Api(tags = "Multi-Factor/Two Factor APIs", description = "API endpoints for Two-Factor Authentication")
public class TwoFactorController {

	@Autowired
	private TwoFactorService twoFactorService;

	@ApiOperation(value = "Generate QR Code for Authenticator APP")
	@GetMapping(value = "/generateQRCode/{secretBase32}", produces = MediaType.IMAGE_PNG_VALUE)
	public void generateQRCode(@PathVariable String secretBase32, HttpServletResponse response)
			throws IOException, WriterException {

		Base32 base32 = new Base32();
		byte[] secretBytes = base32.decode(secretBase32);
		String otpAuthURI = "otpauth://totp/Login?secret=" + Base64.getEncoder().encodeToString(secretBytes);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(otpAuthURI, BarcodeFormat.QR_CODE, 200, 200);
		response.setContentType(MediaType.IMAGE_PNG_VALUE);
		MatrixToImageWriter.writeToStream(bitMatrix, "PNG", response.getOutputStream());
	}

	@ApiOperation(value = "Get Secret for Authenticator App")
	@ApiResponses({ @ApiResponse(code = 200, message = "The request was successful", response = SecretResponse.class),
			@ApiResponse(code = 401, message = "You did not supply a valid Authorization header"),
			@ApiResponse(code = 500, message = "There was an internal error"),
			@ApiResponse(code = 503, message = "The search index is not available or encountered an exception so the request cannot be completed") })
	@GetMapping(value = "/getAuthenticatorSecret", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getAuthenticatorSecret(HttpServletRequest httpServletRequest) throws CommonNotFound {

		log.info("Get authenticator secret request start");
		SecretResponse response = twoFactorService.getAuthenticatorSecret(httpServletRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Enable Two-Factor for Authenticator App")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The request was successful. Multi-Factor has been enabled for the User", response = TwoFactorResponse.class),
			@ApiResponse(code = 400, message = "The request was invalid and/or malformed"),
			@ApiResponse(code = 401, message = "You did not supply a valid Authorization header"),
			@ApiResponse(code = 404, message = "The User does not exist"),
			@ApiResponse(code = 421, message = "The code request parameter is not valid"),
			@ApiResponse(code = 500, message = "There was an internal error"),
			@ApiResponse(code = 503, message = "The search index is not available or encountered an exception so the request cannot be completed") })
	@PostMapping(value = "/enableTwoFactorAuthenticator", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> enableTwoFactorForAuthenticator(@RequestBody TwoFactorAuthenticatorRequest request,
			HttpServletRequest httpServletRequest) throws CommonNotFound {

		log.info("Enable two-factor for google authenticator app request start");
		TwoFactorResponse response = twoFactorService.enableTwoFactorForAuthenticator(request, httpServletRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Send Two-Factor code for enable/disable for email method")
	@ApiResponses({ @ApiResponse(code = 200, message = "The request was successful", response = Boolean.class),
			@ApiResponse(code = 400, message = "The request was invalid and/or malformed"),
			@ApiResponse(code = 401, message = "You did not supply a valid Authorization header"),
			@ApiResponse(code = 500, message = "There was an internal error"),
			@ApiResponse(code = 503, message = "The search index is not available or encountered an exception so the request cannot be completed") })
	@PostMapping(value = "/sendTwoFactorCodeEnableDisableEmail", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> sendTwoFactorCodeEnableDisableEmail(@RequestBody TwoFactorEmailRequest request,
			HttpServletRequest httpServletRequest) throws CommonNotFound {

		log.info("Enable two-factor for google authenticator app request start");
		Boolean response = twoFactorService.sendTwoFactorCodeForEnableDisableEmail(request, httpServletRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Enable Two-Factor for Email")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The request was successful. Multi-Factor has been enabled for the User", response = TwoFactorResponse.class),
			@ApiResponse(code = 400, message = "The request was invalid and/or malformed"),
			@ApiResponse(code = 401, message = "You did not supply a valid Authorization header"),
			@ApiResponse(code = 404, message = "The User does not exist"),
			@ApiResponse(code = 421, message = "The code request parameter is not valid"),
			@ApiResponse(code = 500, message = "There was an internal error"),
			@ApiResponse(code = 503, message = "The search index is not available or encountered an exception so the request cannot be completed") })
	@PostMapping(value = "/enableTwoFactorEmail", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> enableTwoFactorForEmail(@RequestBody EnableTwoFactorEmailRequest request,
			HttpServletRequest httpServletRequest) throws CommonNotFound {

		log.info("Enable two-factor for email request start");
		TwoFactorResponse response = twoFactorService.enableTwoFactorForEmail(request, httpServletRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Send Two-Factor code for enable/disable for sms method")
	@ApiResponses({ @ApiResponse(code = 200, message = "The request was successful", response = Boolean.class),
			@ApiResponse(code = 400, message = "The request was invalid and/or malformed"),
			@ApiResponse(code = 401, message = "You did not supply a valid Authorization header"),
			@ApiResponse(code = 500, message = "There was an internal error"),
			@ApiResponse(code = 503, message = "The search index is not available or encountered an exception so the request cannot be completed") })
	@PostMapping(value = "/sendTwoFactorCodeEnableDisableSms", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> sendTwoFactorCodeEnableDisableSms(@RequestBody TwoFactorSmsRequest request,
			HttpServletRequest httpServletRequest) throws CommonNotFound {

		log.info("Enable two-factor for sms request start");
		Boolean response = twoFactorService.sendTwoFactorCodeForEnableDisableSms(request, httpServletRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Enable Two-Factor for Sms")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The request was successful. Multi-Factor has been enabled for the User", response = TwoFactorResponse.class),
			@ApiResponse(code = 400, message = "The request was invalid and/or malformed"),
			@ApiResponse(code = 401, message = "You did not supply a valid Authorization header"),
			@ApiResponse(code = 404, message = "The User does not exist"),
			@ApiResponse(code = 421, message = "The code request parameter is not valid"),
			@ApiResponse(code = 500, message = "There was an internal error"),
			@ApiResponse(code = 503, message = "The search index is not available or encountered an exception so the request cannot be completed") })
	@PostMapping(value = "/enableTwoFactorForSms", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> enableTwoFactorForSms(@RequestBody EnableTwoFactorSmsRequest request,
			HttpServletRequest httpServletRequest) throws CommonNotFound {

		log.info("Enable two-factor for sms request start");
		TwoFactorResponse response = twoFactorService.enableTwoFactorForSms(request, httpServletRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Generate Two-Factor Recovery Code")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The request was successful", response = TwoFactorRecoveryCodeResponse.class),
			@ApiResponse(code = 400, message = "The request was invalid and/or malformed"),
			@ApiResponse(code = 401, message = "You did not supply a valid Authorization header"),
			@ApiResponse(code = 500, message = "There was an internal error") })
	@PostMapping(value = "/generateRecoveryCode/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> generateRecoveryCode(
			@ApiParam(value = "UserId of user", example = "dccc6812-1623-4ddb-818c-6cdc9f6bb24a") @PathVariable String userId,
			HttpServletRequest httpServletRequest) throws CommonNotFound {

		log.info("Generate Two-Factor Recovery Code request start");
		TwoFactorRecoveryCodeResponse response = twoFactorService.generateRecoveryCode(userId, httpServletRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Retriveve Two-Factor Recovery Code")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The request was successful", response = TwoFactorRecoveryCodeResponse.class),
			@ApiResponse(code = 400, message = "The request was invalid and/or malformed"),
			@ApiResponse(code = 401, message = "You did not supply a valid Authorization header"),
			@ApiResponse(code = 500, message = "There was an internal error") })
	@GetMapping(value = "/retrieveRecoveryCode/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> retrieveRecoveryCode(
			@ApiParam(value = "UserId of user", example = "dccc6812-1623-4ddb-818c-6cdc9f6bb24a") @PathVariable String userId,
			HttpServletRequest httpServletRequest) throws CommonNotFound {

		log.info("Retrieve Two-Factor Recovery Code request start");
		TwoFactorRecoveryCodeResponse response = twoFactorService.retrieveRecoveryCode(userId, httpServletRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Send Two-Factor code for Login")
	@ApiResponses({ @ApiResponse(code = 200, message = "The request was successful", response = Boolean.class),
			@ApiResponse(code = 400, message = "The request was invalid and/or malformed"),
			@ApiResponse(code = 401, message = "You did not supply a valid Authorization header"),
			@ApiResponse(code = 500, message = "There was an internal error"),
			@ApiResponse(code = 503, message = "The search index is not available or encountered an exception so the request cannot be completed") })
	@PostMapping(value = "/sendTwoFactorCodeForLogin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> sendTwoFactorCodeForLoginUsingMethod(@RequestBody TwoFactorSendCodeRequest request,
			HttpServletRequest httpServletRequest) throws CommonNotFound {

		log.info("Send two-factor code request start");
		Boolean response = twoFactorService.sendTwoFactorCodeForLoginUsingMethod(request, httpServletRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Two-Factor Login")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The authentication was successful", response = LoginResponse.class),
			@ApiResponse(code = 202, message = "The user was authenticated successfully. The user is not registered for the application specified by the applicationId on the request"),
			@ApiResponse(code = 203, message = "The user was authenticated successfully. The user is required to change their password, the response will contain the changePasswordId to be used on the Change Password API"),
			@ApiResponse(code = 212, message = "The user’s email address has not yet been verified"),
			@ApiResponse(code = 213, message = "The user’s registration has not yet been verified"),
			@ApiResponse(code = 400, message = "The request was invalid and/or malformed"),
			@ApiResponse(code = 401, message = "You did not supply a valid Authorization header"),
			@ApiResponse(code = 404, message = "The twoFactorId was invalid or expired. You will need to authenticate again using the Login"),
			@ApiResponse(code = 409, message = "The user is currently in an action that has prevented login"),
			@ApiResponse(code = 410, message = "The user has expired. The response will be empty"),
			@ApiResponse(code = 421, message = "The code request parameter is not valid"),
			@ApiResponse(code = 500, message = "There was an internal error"),
			@ApiResponse(code = 503, message = "The search index is not available or encountered an exception so the request cannot be completed"),
			@ApiResponse(code = 504, message = "One or more Webhook endpoints returned an invalid response or were unreachable") })
	@PostMapping(value = "/twoFactorLogin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> twoFactorLogin(@RequestBody TwoFactorLogin request, HttpServletRequest httpServletRequest)
			throws CommonNotFound {

		log.info("Two-Factor Login request start");
		LoginResponse response = twoFactorService.loginTwoFactor(request, httpServletRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}

package com.fusion.sso.controller;

import com.fusion.sso.dto.*;
import com.fusion.sso.exception.CommonNotFound;
import com.fusion.sso.service.AuthService;
import io.fusionauth.domain.api.LoginResponse;
import io.fusionauth.domain.api.UserResponse;
import io.fusionauth.domain.api.jwt.ValidateResponse;
import io.fusionauth.domain.api.user.ForgotPasswordResponse;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RestController
@RequestMapping("/auth")
@Slf4j
@Api(tags = "User API", description = "API endpoints to create,update,change password,get user,login")
public class AuthController {

	@Autowired
	private AuthService authService;

	@ApiOperation(value = "Create User")
	@ApiResponses({ @ApiResponse(code = 200, message = "The request was successful", response = UserResponse.class),
			@ApiResponse(code = 400, message = "The request was invalid and/or malformed"),
			@ApiResponse(code = 401, message = "You did not supply a valid Authorization header"),
			@ApiResponse(code = 500, message = "There was an internal error"),
			@ApiResponse(code = 503, message = "The search index is not available or encountered an exception so the request cannot be completed"),
			@ApiResponse(code = 504, message = "One or more Webhook endpoints returned an invalid response or were unreachable") })
	@PostMapping(value = "/createUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> createUser(@RequestBody CreateUser request, HttpServletRequest httpServletRequest)
			throws CommonNotFound {
		log.info("Create user request start");
		UserResponse response = authService.createUser(request, httpServletRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@ApiOperation(value = "Updatd User")
	@ApiResponses({ @ApiResponse(code = 200, message = "Successfully updated user", response = UserResponse.class),
			@ApiResponse(code = 400, message = "The request was invalid and/or malformed"),
			@ApiResponse(code = 401, message = "You did not supply a valid Authorization header"),
			@ApiResponse(code = 500, message = "There was an internal error"),
			@ApiResponse(code = 503, message = "The search index is not available or encountered an exception so the request cannot be completed"),
			@ApiResponse(code = 504, message = "One or more Webhook endpoints returned an invalid response or were unreachable") })
	@PutMapping(value = "/updateUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateUser(@RequestBody UpdateUser request, HttpServletRequest httpServletRequest)
			throws CommonNotFound {
		log.info("Update user request start");
		UserResponse response = authService.updateUser(request, httpServletRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Login")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The authentication was successful", response = LoginResponse.class),
			@ApiResponse(code = 202, message = "The user was authenticated successfully. The user is not registered for the application specified by the applicationId on the request"),
			@ApiResponse(code = 203, message = "The user was authenticated successfully. The user is required to change their password, the response will contain the changePasswordId to be used on the Change Password API"),
			@ApiResponse(code = 212, message = "The user’s email address has not yet been verified"),
			@ApiResponse(code = 213, message = "The user’s registration has not yet been verified"),
			@ApiResponse(code = 242, message = "The user was authenticated successfully. The user has two factor authentication enabled"),
			@ApiResponse(code = 400, message = "The request was invalid and/or malformed"),
			@ApiResponse(code = 401, message = "You did not supply a valid Authorization header"),
			@ApiResponse(code = 404, message = "The user was not found or the password was incorrect"),
			@ApiResponse(code = 409, message = "The user is currently in an action that has prevented login"),
			@ApiResponse(code = 410, message = "The user has expired. The response will be empty"),
			@ApiResponse(code = 423, message = "The user is locked and cannot login"),
			@ApiResponse(code = 500, message = "There was an internal error"),
			@ApiResponse(code = 503, message = "The search index is not available or encountered an exception so the request cannot be completed"),
			@ApiResponse(code = 504, message = "One or more Webhook endpoints returned an invalid response or were unreachable") })
	@PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> login(@RequestBody Login request, HttpServletRequest httpServletRequest)
			throws CommonNotFound {

		log.info("Login request start");
		LoginResponse response = authService.login(request, httpServletRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Get User by Id")
	@ApiResponses({ @ApiResponse(code = 200, message = "The request was successful", response = UserResponse.class),
			@ApiResponse(code = 400, message = "The request was invalid and/or malformed"),
			@ApiResponse(code = 401, message = "You did not supply a valid Authorization header"),
			@ApiResponse(code = 404, message = "The object you requested doesn’t exist"),
			@ApiResponse(code = 500, message = "There was an internal error"),
			@ApiResponse(code = 503, message = "The search index is not available or encountered an exception so the request cannot be completed") })
	@GetMapping(value = "/getUserById/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserById(
			@ApiParam(value = "UserId of user", example = "dccc6812-1623-4ddb-818c-6cdc9f6bb24a") @PathVariable String userId,
			HttpServletRequest httpServletRequest) throws CommonNotFound {

		log.info("Get user by id request start");
		UserResponse response = authService.getUserById(userId, httpServletRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Get User By Email")
	@ApiResponses({ @ApiResponse(code = 200, message = "The request was successful", response = UserResponse.class),
			@ApiResponse(code = 400, message = "The request was invalid and/or malformed"),
			@ApiResponse(code = 401, message = "You did not supply a valid Authorization header"),
			@ApiResponse(code = 404, message = "The object you requested doesn’t exist"),
			@ApiResponse(code = 500, message = "There was an internal error"),
			@ApiResponse(code = 503, message = "The search index is not available or encountered an exception so the request cannot be completed") })
	@GetMapping(value = "/getUserByEmail/{email}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getUserByEmail(
			@ApiParam(value = "EmailId of user", example = "something@example.com") @PathVariable String email,
			HttpServletRequest httpServletRequest) throws CommonNotFound {

		log.info("Get user by email request start");
		UserResponse response = authService.getUserByEmail(email, httpServletRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Change Password")
	@ApiResponses({ @ApiResponse(code = 200, message = "The request was successful"),
			@ApiResponse(code = 400, message = "The request was invalid and/or malformed"),
			@ApiResponse(code = 401, message = "You did not supply a valid Authorization header"),
			@ApiResponse(code = 404, message = "The User could not be found using the changePasswordId or loginId value from the request"),
			@ApiResponse(code = 500, message = "There was an internal error"),
			@ApiResponse(code = 503, message = "The search index is not available or encountered an exception so the request cannot be completed") })
	@PutMapping(value = "/changePassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> changePassword(@RequestBody ChangePassword request, HttpServletRequest httpServletRequest)
			throws CommonNotFound {
		log.info("Change password request start");
		authService.changePassword(request, httpServletRequest);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@ApiOperation(value = "Forgot Password")
	@ApiResponses({
			@ApiResponse(code = 200, message = "The request was successful", response = ForgotPasswordResponse.class),
			@ApiResponse(code = 400, message = "The request was invalid and/or malformed"),
			@ApiResponse(code = 401, message = "You did not supply a valid Authorization header"),
			@ApiResponse(code = 404, message = "The User could not be found using the changePasswordId or loginId value from the request"),
			@ApiResponse(code = 500, message = "There was an internal error"),
			@ApiResponse(code = 503, message = "The search index is not available or encountered an exception so the request cannot be completed") })
	@PutMapping(value = "/forgotPassword", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> forgotPassword(@RequestBody ForgotPassword request, HttpServletRequest httpServletRequest)
			throws CommonNotFound {
		log.info("Forgot password request start");
		ForgotPasswordResponse response = authService.forgotPassword(request, httpServletRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Deactivate User")
	@ApiResponses({ @ApiResponse(code = 200, message = "The request was successful"),
			@ApiResponse(code = 400, message = "The request was invalid and/or malformed"),
			@ApiResponse(code = 401, message = "You did not supply a valid Authorization header"),
			@ApiResponse(code = 404, message = "The object you are trying to update doesn’t exist"),
			@ApiResponse(code = 500, message = "There was an internal error"),
			@ApiResponse(code = 503, message = "The search index is not available or encountered an exception so the request cannot be completed"),
			@ApiResponse(code = 504, message = "One or more Webhook endpoints returned an invalid response or were unreachable") })
	@PutMapping(value = "/deactivateUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> deactivateUser(@RequestBody ActivateDeactivate request,
			HttpServletRequest httpServletRequest) throws CommonNotFound {
		log.info("Deactivate user request start");
		authService.deactivateUserById(request, httpServletRequest);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@ApiOperation(value = "Reactivate User")
	@ApiResponses({ @ApiResponse(code = 200, message = "The request was successful", response = UserResponse.class),
			@ApiResponse(code = 400, message = "The request was invalid and/or malformed"),
			@ApiResponse(code = 401, message = "You did not supply a valid Authorization header"),
			@ApiResponse(code = 404, message = "The object you are trying to update doesn’t exist"),
			@ApiResponse(code = 500, message = "There was an internal error"),
			@ApiResponse(code = 503, message = "The search index is not available or encountered an exception so the request cannot be completed"),
			@ApiResponse(code = 504, message = "One or more Webhook endpoints returned an invalid response or were unreachable") })
	@PutMapping(value = "/reactivateUser", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> reactivateUser(@RequestBody ActivateDeactivate request,
			HttpServletRequest httpServletRequest) throws CommonNotFound {
		log.info("Reactivate user request start");
		UserResponse response = authService.reactivateUserById(request, httpServletRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Validate JWT Token")
	@ApiResponses({ @ApiResponse(code = 200, message = "The request was successful", response = ValidateResponse.class),
			@ApiResponse(code = 401, message = "The access token is not valid"),
			@ApiResponse(code = 500, message = "There was an internal error") })
	@PutMapping(value = "/validateJwt", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> validateJwt(@RequestBody ValidateJWT request, HttpServletRequest httpServletRequest)
			throws CommonNotFound {
		log.info("Validate JWT request start");
		ValidateResponse response = authService.validateJwt(request, httpServletRequest);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}

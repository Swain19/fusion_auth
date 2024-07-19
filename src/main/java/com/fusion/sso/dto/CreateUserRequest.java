package com.fusion.sso.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fusion.sso.common.DataobjectApis;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "User details")
public class CreateUserRequest implements DataobjectApis {

	@ApiModelProperty(value = "FusionAuth user id", example = "")
	private String userId;

	@ApiModelProperty(value = "FusionAuth username", example = "")
	private String userName;

	@ApiModelProperty(value = "Email of user", example = "something@example.com")
	private String email;

	@ApiModelProperty(value = "Password of user", example = "Password@123")
	private String password;

	@ApiModelProperty(value = "CurrentPassword of user", example = "")
	private String currentPassword;

	@ApiModelProperty(value = "FirstName od user", example = "firstname")
	private String firstName;

	@ApiModelProperty(value = "LastName of user", example = "lastname")
	private String lastName;

	@ApiModelProperty(value = "MiddleName of user", example = "middlename")
	private String middleName;

	@ApiModelProperty(value = "FullName of user", example = "fullname")
	private String fullName;

	@ApiModelProperty(value = "MobilePhone of user", example = "1234567890")
	private String mobilePhone;

	@ApiModelProperty(value = "Date of Birth of user", example = "")
	private LocalDate dob;

	private String status;
	private int statusCode;
	private Boolean isSuccess;
	private Date passwordCreatedDate;
	private String changePasswordId;

	private String token;

	@Override
	public String toJson() {
		return null;
	}

}

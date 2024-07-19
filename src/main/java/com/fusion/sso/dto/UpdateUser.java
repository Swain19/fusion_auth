package com.fusion.sso.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fusion.sso.common.DataobjectApis;
import com.google.gson.Gson;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

/**
 * Data object for update a user.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "User details")
public class UpdateUser implements DataobjectApis {

	@ApiModelProperty(value = "FusionAuth user id", example = "627ca85d-6c8b-4ba1-9140-676f175aaee8")
	private String userId;

	@ApiModelProperty(value = "Email of user", example = "something@example.com")
	private String email;

	@ApiModelProperty(value = "Password of user", example = "Password@123")
	private String password;

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

	@Override
	public String toJson() {
		return new Gson().toJson(this);
	}

}

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

/**
 * Data object for changing a user's password.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Change Password")
public class ChangePassword implements DataobjectApis {

	@ApiModelProperty(value = "Email of user", example = "something@example.com")
	private String email;

	@ApiModelProperty(value = "Password of user", example = "Password@123")
	private String password;

	@ApiModelProperty(value = "CurrentPassword of user", example = "")
	private String currentPassword;

	@ApiModelProperty(value = "ChangePasswordId of user incase of forgot password request", example = "")
	private String changePasswordId;

	@Override
	public String toJson() {
		return new Gson().toJson(this);
	}

}

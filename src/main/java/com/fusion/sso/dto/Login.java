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
 * Data object for login request.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Two-Factor Login")
public class Login implements DataobjectApis {

	@ApiModelProperty(value = "Email Address", example = "subhransu@mailinator.com")
	private String email;

	@ApiModelProperty(value = "Password", example = "Password@123")
	private String password;

	@Override
	public String toJson() {
		return new Gson().toJson(this);
	}

}

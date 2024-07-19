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
 * Data object for forgot password.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Forgot Password")
public class ForgotPassword implements DataobjectApis {

	@ApiModelProperty(value = "Email of user", example = "something@example.com")
	private String email;

	@Override
	public String toJson() {
		return new Gson().toJson(this);
	}

}

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
 * Data object for enabling two-factor authentication via authenicator app.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Two-Factor Enable Authenticator")
public class TwoFactorAuthenticatorRequest implements DataobjectApis {

	@ApiModelProperty(value = "FusionAuth user id", example = "976b1df3-7f35-45d1-86c1-4dc65ccfb5fa")
	private String userId;

	@ApiModelProperty(value = "Two-Factor Code", example = "123456")
	private String code;

	@ApiModelProperty(value = "Two-Factor method", example = "authenticator")
	private String method;

	@ApiModelProperty(value = "A base64 encoded secret", example = "dWi4hd+hW+C/z2mOSxBk")
	private String secret;

	@Override
	public String toJson() {
		return new Gson().toJson(this);
	}

}

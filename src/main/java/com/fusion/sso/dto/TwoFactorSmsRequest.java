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
 * Data object for two-factor authentication via sms.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Two-Factor SMS")
public class TwoFactorSmsRequest implements DataobjectApis {

	@ApiModelProperty(value = "FusionAuth user id", example = "")
	private String userId;

	@ApiModelProperty(value = "Two-Factor mobile number", example = "1234567890")
	private String mobilePhone;

	@ApiModelProperty(value = "Two-Factor method", example = "sms")
	private String method;

	@Override
	public String toJson() {
		return new Gson().toJson(this);
	}

}

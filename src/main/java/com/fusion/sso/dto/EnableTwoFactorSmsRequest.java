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
 * Data object for enabling two-factor authentication via sms.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Two-Factor Enable SMS")
public class EnableTwoFactorSmsRequest implements DataobjectApis {

	@ApiModelProperty(value = "FusionAuth user id", example = "627ca85d-6c8b-4ba1-9140-676f175aaee8")
	private String userId;

	@ApiModelProperty(value = "Two-Factor Code", example = "123456")
	private String code;

	@ApiModelProperty(value = "Two-Factor method", example = "sms")
	private String method;

	@ApiModelProperty(value = "Two-Factor mobile number", example = "1234567890")
	private String mobilePhone;

	@Override
	public String toJson() {
		return new Gson().toJson(this);
	}

}

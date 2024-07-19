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
 * Data object for sending two-factor code.
 */
@Data
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(description = "Two-Factor Send Code")
public class TwoFactorSendCodeRequest implements DataobjectApis {

	@ApiModelProperty(value = "Login Two-Factor Id", example = "99CeKQA2CivDTMPBbLQoccvxAVj0jdPQHMKXe53pRbM")
	private String twoFactorId;

	@ApiModelProperty(value = "Two-Factor Method Id", example = "M8JC")
	private String methodId;

	@Override
	public String toJson() {
		return new Gson().toJson(this);
	}

}

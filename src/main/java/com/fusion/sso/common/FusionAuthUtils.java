package com.fusion.sso.common;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Utility class for managing FusionAuth configuration properties.
 */
@Getter
@Component
public class FusionAuthUtils {

	@Value("${fusionauth.apiKey}")
	private String fusionAuthApiKey;

	@Value("${fusionauth.baseUrl}")
	private String fusionAuthBaseUrl;

	@Value("${fusionauth.tenantId}")
	private String tenantId;

	@Value("${fusionauth.applicationId}")
	private String applicationId;

}

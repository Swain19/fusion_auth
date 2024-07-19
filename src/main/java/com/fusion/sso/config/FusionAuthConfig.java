package com.fusion.sso.config;

import com.fusion.sso.common.FusionAuthUtils;
import io.fusionauth.client.FusionAuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FusionAuthConfig {

	@Autowired
	private FusionAuthUtils fusionAuthUtils;

	@Bean
	FusionAuthClient getFusionAuthClient() {
		return new FusionAuthClient(fusionAuthUtils.getFusionAuthApiKey(), fusionAuthUtils.getFusionAuthBaseUrl());
	}
}

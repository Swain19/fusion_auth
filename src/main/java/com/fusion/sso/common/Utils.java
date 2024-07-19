package com.fusion.sso.common;

import com.inversoft.error.Error;
import com.inversoft.error.Errors;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Log4j2
public class Utils {

	public String mapFusionAuthError(Errors errorResponse) {
		log.error(errorResponse);
		StringBuilder errorMessage = new StringBuilder();

		Map<String, List<Error>> fieldErrors = errorResponse.fieldErrors;
		List<Error> generalErrors = errorResponse.generalErrors;

		if (!fieldErrors.keySet().isEmpty()) {
			fieldErrors.keySet().stream().forEach(data -> {
				List<Error> errors = fieldErrors.get(data);
				if (!errors.isEmpty()) {
					for (Error error : errors) {
						if (!error.code.contains("username"))
							errorMessage.append(error.message);
					}
				}
			});
		}

		if (!generalErrors.isEmpty()) {
			for (Error error : generalErrors) {
				errorMessage.append(error.message);
			}
		}
		return errorMessage.toString();
	}
}

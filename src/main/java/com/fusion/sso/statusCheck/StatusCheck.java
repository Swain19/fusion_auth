package com.fusion.sso.statusCheck;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/status")
@Log4j2
@CrossOrigin
public class StatusCheck {

	@GetMapping(value = "/health")
	@ResponseStatus(value = HttpStatus.OK)
	public void HealthCheck() {
		log.info("Status check");
	}
}

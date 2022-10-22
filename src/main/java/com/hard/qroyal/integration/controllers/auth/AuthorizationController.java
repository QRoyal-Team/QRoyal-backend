package com.hard.qroyal.integration.controllers.auth;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/authorization")
public class AuthorizationController {

	@GetMapping("/admin")
	public String adminAcess() {
		return "Admin access";
	}

	@GetMapping("/staff")
	public String staffAccess() {
		return "Staff access";
	}

	@GetMapping("/client")
	public String clientAccess() {
		return "Client access";
	}
}

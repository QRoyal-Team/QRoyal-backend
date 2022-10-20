package com.hard.qroyal.integration.controllers.auth;

import com.hard.qroyal.auth.MyUserDetails;
import com.hard.qroyal.auth.jwt.JwtTokenProvider;
import com.hard.qroyal.domain.payloads.auth.LoginRequest;
import com.hard.qroyal.domain.payloads.auth.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@PostMapping("/auth/login")
	public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
						loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateToken((MyUserDetails) authentication.getPrincipal());
		return new LoginResponse(jwt);
	}

	@GetMapping("/auth/admin")
	public String adminAcess() {
		return "Admin access";
	}

	@GetMapping("/auth/staff")
	public String staffAccess() {
		return "Staff access";
	}

	@GetMapping("/auth/client")
	public String clientAccess() {
		return "Client access";
	}
}

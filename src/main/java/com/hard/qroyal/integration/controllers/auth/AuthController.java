package com.hard.qroyal.integration.controllers.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hard.qroyal.auth.MyUserDetails;
import com.hard.qroyal.auth.jwt.JwtTokenProvider;
import com.hard.qroyal.domain.dtos.auth.request.LoginRequest;
import com.hard.qroyal.domain.dtos.auth.request.ReSendOtpRequest;
import com.hard.qroyal.domain.dtos.auth.request.RegisterRequest;
import com.hard.qroyal.domain.dtos.auth.request.VerifyRequest;
import com.hard.qroyal.domain.dtos.auth.response.LoginResponse;
import com.hard.qroyal.domain.entities.User;
import com.hard.qroyal.infrastructure.services.commands.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenProvider tokenProvider;

	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public LoginResponse login(@Valid @RequestBody LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
						loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = tokenProvider.generateToken((MyUserDetails) authentication.getPrincipal());
		return new LoginResponse(jwt);
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest registerRequest)
			throws MessagingException, UnsupportedEncodingException {
		User user = objectMapper.convertValue(registerRequest, User.class);
		userService.register(user);
		return ResponseEntity.ok("Resgister sucessfully");
	}

	@PostMapping("/resendOtp")
	public ResponseEntity<?> resendOtp(@Valid @RequestBody ReSendOtpRequest reSendOtpRequest)
			throws MessagingException, UnsupportedEncodingException {
		userService.reSendVerificationEmail(reSendOtpRequest.getUsername());
		return ResponseEntity.ok("Resend sucessfully");
	}

	@PostMapping("/verify")
	public ResponseEntity<?> register(@Valid @RequestBody VerifyRequest verifyRequest) {
		String resulf = userService.verifyOtp(verifyRequest.getUsername(), verifyRequest.getOtpCode());
		return ResponseEntity.ok(resulf);
	}
}

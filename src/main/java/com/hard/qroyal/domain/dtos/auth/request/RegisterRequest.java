package com.hard.qroyal.domain.dtos.auth.request;

import lombok.Data;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class RegisterRequest {

	@NotBlank
	private String name;

	@Email(message = "{Register email invalid}")
	private String username;

	@Pattern(regexp = "^(?=\\P{Ll}*\\p{Ll})(?=\\P{Lu}*\\p{Lu})(?=\\P{N}*\\p{N})(?=[\\p{L}\\p{N}]*[^\\p{L}\\p{N}])[\\s\\S]{8,20}$", message =
			"Password must contain 8 to 20 characters at least one digit,"
					+ " lower, upper case and one special character.")
	private String password;
}

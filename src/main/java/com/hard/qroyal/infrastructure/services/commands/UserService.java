package com.hard.qroyal.infrastructure.services.commands;

import com.hard.qroyal.domain.entities.User;
import com.hard.qroyal.infrastructure.services.BaseService;
import org.springframework.security.core.userdetails.UserDetails;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface UserService extends BaseService<User> {

	public User findByUsername(String username);

	public UserDetails loadUserById(Long id);

	public void register(User user) throws MessagingException, UnsupportedEncodingException;

	public void reSendVerificationEmail(String username)
			throws MessagingException, UnsupportedEncodingException;

	public void sendVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException;

	public String verifyOtp(String username, String otpCode);

	public User getCurrentUser();
}

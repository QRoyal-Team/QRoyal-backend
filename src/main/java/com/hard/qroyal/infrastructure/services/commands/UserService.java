package com.hard.qroyal.infrastructure.services.commands;

import com.hard.qroyal.domain.entities.User;
import com.hard.qroyal.infrastructure.services.BaseService;
import org.springframework.security.core.userdetails.UserDetails;
import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface UserService extends BaseService<User> {

	public UserDetails loadUserById(Long id);

	public void register(User user) throws MessagingException, UnsupportedEncodingException;

	public boolean verifyOtp(String username, String otpCode);
}

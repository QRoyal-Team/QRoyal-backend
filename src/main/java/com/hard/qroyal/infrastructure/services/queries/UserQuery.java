package com.hard.qroyal.infrastructure.services.queries;

import com.hard.qroyal.auth.MyUserDetails;
import com.hard.qroyal.domain.entities.Role;
import com.hard.qroyal.domain.entities.RoleAssignment;
import com.hard.qroyal.domain.entities.User;
import com.hard.qroyal.infrastructure.repositories.respositories.UserRepository;
import com.hard.qroyal.infrastructure.services.BaseQuery;
import com.hard.qroyal.infrastructure.services.commands.OtpMessageService;
import com.hard.qroyal.infrastructure.services.commands.RoleService;
import com.hard.qroyal.infrastructure.services.commands.UserService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserQuery extends BaseQuery<User, UserRepository> implements UserService, UserDetailsService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private OtpMessageService otpMessageService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new MyUserDetails(user);
	}

	@Override
	public User findByUsername(String username) {
		return repository.findByUsername(username);
	}

	@Override
	public UserDetails loadUserById(Long id) {
		User user = repository.findById(id).orElse(null);
		if (user == null) {
			throw new ObjectNotFoundException("Not found user with id: ", id.toString());
		}
		return new MyUserDetails(user);
	}

	@Override
	public void register(User user) throws MessagingException, UnsupportedEncodingException {
		if (repository.findByUsername(user.getUsername()) != null) {
			reSendVerificationEmail(user.getUsername());
		}
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		user.setEnabled(false);
		user.setOtpMessage(otpMessageService.generateOtp(user));
		User savedUser = repository.save(user);
		sendVerificationEmail(savedUser);
	}

	@Override
	public void reSendVerificationEmail(String username)
			throws MessagingException, UnsupportedEncodingException {
		User user = repository.findByUsername(username);
		user.setOtpMessage(otpMessageService.regenerateOtp(user));
		User userSaved = repository.save(user);
		sendVerificationEmail(userSaved);
	}

	@Override
	public void sendVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException {
		String toAddress = user.getUsername();
		String fromAddress = "shopjavaweb@gmail.com";
		String senderName = "QRoyal Welcome";
		String subject = "Please verify your registration";
		String content = "Dear [[name]],<br>" + "Please enter your OTP to registration<br>"
				+ "<h3>OTP CODE</h3><h2>[[OTP]]</h2>" + "Thank you,<br>" + "QRoyal Team";
		MimeMessage message = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);
		helper.setFrom(fromAddress, senderName);
		helper.setTo(toAddress);
		helper.setSubject(subject);
		content = content.replace("[[name]]", user.getName());
		content = content.replace("[[OTP]]", user.getOtpMessage().getRandomCode());
		helper.setText(content, true);
		javaMailSender.send(message);
	}

	@Override
	public String verifyOtp(String username, String otpCode) {
		User user = repository.findByUsername(username);
		if (user.getEnabled()) {
			return "Enabled";
		}
		if (otpCode.equals(user.getOtpMessage().getRandomCode())) {
			if (Duration.between(LocalDateTime.now(), user.getOtpMessage().getExpired()).getSeconds() > 0) {
				user.setEnabled(true);
				Role role = roleService.findByName("ROLE_CLIENT");
				RoleAssignment roleAssignment = new RoleAssignment(user, role);
				List<RoleAssignment> roleAssignments = new ArrayList<>();
				roleAssignments.add(roleAssignment);
				user.setRoleAssignments(roleAssignments);
				repository.save(user);
				return "Verify Success";
			}
			return "OTP expired";
		}
		return "OTP invalid";
	}

	@Override
	public User getCurrentUser() {
		try {
			UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
					.getPrincipal();
			User user = repository.findByUsername(userDetails.getUsername());
			return user;
		} catch (Exception e) {
			return null;
		}
	}
}

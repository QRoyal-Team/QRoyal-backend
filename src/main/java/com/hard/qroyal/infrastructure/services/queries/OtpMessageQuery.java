package com.hard.qroyal.infrastructure.services.queries;

import com.hard.qroyal.domain.entities.OtpMessage;
import com.hard.qroyal.domain.entities.User;
import com.hard.qroyal.infrastructure.repositories.respositories.OtpMessageRepository;
import com.hard.qroyal.infrastructure.services.BaseQuery;
import com.hard.qroyal.infrastructure.services.commands.OtpMessageService;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class OtpMessageQuery extends BaseQuery<OtpMessage, OtpMessageRepository>
		implements OtpMessageService {

	@Override
	public OtpMessage generateOtp(User user) {
		String otpCode = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999));
		LocalDateTime created = LocalDateTime.now();
		LocalDateTime expired = created.plus(Duration.of(3, ChronoUnit.MINUTES));
		OtpMessage otpMessage = new OtpMessage();
		otpMessage.setRandomCode(otpCode);
		otpMessage.setCreated(created);
		otpMessage.setExpired(expired);
		otpMessage.setUser(user);
		return otpMessage;
	}

	@Override
	public OtpMessage regenerateOtp(User user) {
		String otpCode = String.valueOf(ThreadLocalRandom.current().nextInt(1000, 9999));
		LocalDateTime created = LocalDateTime.now();
		LocalDateTime expired = created.plus(Duration.of(3, ChronoUnit.MINUTES));
		OtpMessage otpMessage = user.getOtpMessage();
		otpMessage.setRandomCode(otpCode);
		otpMessage.setCreated(created);
		otpMessage.setExpired(expired);
		return otpMessage;
	}
}

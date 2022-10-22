package com.hard.qroyal.infrastructure.services.commands;

import com.hard.qroyal.domain.entities.OtpMessage;
import com.hard.qroyal.domain.entities.User;
import com.hard.qroyal.infrastructure.services.BaseService;

public interface OtpMessageService extends BaseService<OtpMessage> {

	public OtpMessage generateOtp(User user);

	public OtpMessage regenerateOtp(User user);
}

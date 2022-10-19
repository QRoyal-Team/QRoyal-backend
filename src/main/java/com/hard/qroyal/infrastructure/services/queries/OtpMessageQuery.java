package com.hard.qroyal.infrastructure.services.queries;

import com.hard.qroyal.domain.entities.OtpMessage;
import com.hard.qroyal.infrastructure.repositories.OtpMessageRepository;
import com.hard.qroyal.infrastructure.services.BaseQuery;
import com.hard.qroyal.infrastructure.services.commands.OtpMessageService;
import org.springframework.stereotype.Service;

@Service
public class OtpMessageQuery extends BaseQuery<OtpMessage, OtpMessageRepository> implements OtpMessageService {

}

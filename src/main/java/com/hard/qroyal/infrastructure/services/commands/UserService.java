package com.hard.qroyal.infrastructure.services.commands;

import com.hard.qroyal.domain.entities.User;
import com.hard.qroyal.infrastructure.services.BaseService;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService extends BaseService<User> {

	public UserDetails loadUserById(Long id);
}

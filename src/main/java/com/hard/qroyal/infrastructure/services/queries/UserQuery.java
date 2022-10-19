package com.hard.qroyal.infrastructure.services.queries;

import com.hard.qroyal.auth.MyUserDetails;
import com.hard.qroyal.domain.entities.User;
import com.hard.qroyal.infrastructure.repositories.UserRepository;
import com.hard.qroyal.infrastructure.services.BaseQuery;
import com.hard.qroyal.infrastructure.services.commands.UserService;
import org.hibernate.ObjectNotFoundException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserQuery extends BaseQuery<User, UserRepository> implements UserService, UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException(username);
		}
		return new MyUserDetails(user);
	}

	@Override
	public UserDetails loadUserById(Long id) {
		User user = repository.findById(id).orElse(null);
		if (user == null) {
			throw new ObjectNotFoundException("", id.toString());
		}
		return new MyUserDetails(user);
	}
}

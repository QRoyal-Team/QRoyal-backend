package com.hard.qroyal.infrastructure.repositories.respositories;

import com.hard.qroyal.domain.entities.User;
import com.hard.qroyal.infrastructure.repositories.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GenericRepository<User> {

	public User findByUsername(String username);
}

package com.hard.qroyal.infrastructure.repositories.respositories;

import com.hard.qroyal.domain.entities.Role;
import com.hard.qroyal.infrastructure.repositories.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends GenericRepository<Role> {

	public Role findByName(String name);
}

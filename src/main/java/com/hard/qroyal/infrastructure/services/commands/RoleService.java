package com.hard.qroyal.infrastructure.services.commands;

import com.hard.qroyal.domain.entities.Role;
import com.hard.qroyal.infrastructure.services.BaseService;

public interface RoleService extends BaseService<Role> {

	public Role findByName(String name);
}

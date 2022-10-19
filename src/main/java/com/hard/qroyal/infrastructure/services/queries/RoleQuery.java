package com.hard.qroyal.infrastructure.services.queries;

import com.hard.qroyal.domain.entities.Role;
import com.hard.qroyal.infrastructure.repositories.RoleRepository;
import com.hard.qroyal.infrastructure.services.BaseQuery;
import com.hard.qroyal.infrastructure.services.commands.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleQuery extends BaseQuery<Role, RoleRepository> implements RoleService {

}

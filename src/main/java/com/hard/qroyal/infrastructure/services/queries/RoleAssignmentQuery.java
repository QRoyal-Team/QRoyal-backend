package com.hard.qroyal.infrastructure.services.queries;

import com.hard.qroyal.domain.entities.RoleAssignment;
import com.hard.qroyal.infrastructure.repositories.respositories.RoleAssignmentRepository;
import com.hard.qroyal.infrastructure.services.BaseQuery;
import com.hard.qroyal.infrastructure.services.commands.RoleAssignmentService;
import org.springframework.stereotype.Service;

@Service
public class RoleAssignmentQuery extends BaseQuery<RoleAssignment, RoleAssignmentRepository>
		implements RoleAssignmentService {

}

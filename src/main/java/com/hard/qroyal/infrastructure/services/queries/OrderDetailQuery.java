package com.hard.qroyal.infrastructure.services.queries;

import com.hard.qroyal.domain.entities.OrderDetail;
import com.hard.qroyal.infrastructure.repositories.respositories.OrderDetailRepository;
import com.hard.qroyal.infrastructure.services.BaseQuery;
import com.hard.qroyal.infrastructure.services.commands.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailQuery extends BaseQuery<OrderDetail, OrderDetailRepository>
		implements OrderDetailService {

}

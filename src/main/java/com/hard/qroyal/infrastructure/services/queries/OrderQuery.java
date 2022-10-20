package com.hard.qroyal.infrastructure.services.queries;

import com.hard.qroyal.domain.entities.Order;
import com.hard.qroyal.infrastructure.repositories.respositories.OrderRepository;
import com.hard.qroyal.infrastructure.services.BaseQuery;
import com.hard.qroyal.infrastructure.services.commands.OrderService;
import org.springframework.stereotype.Service;

@Service
public class OrderQuery extends BaseQuery<Order, OrderRepository> implements OrderService {

}

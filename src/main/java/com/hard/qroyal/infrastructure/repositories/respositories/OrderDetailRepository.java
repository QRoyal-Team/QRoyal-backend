package com.hard.qroyal.infrastructure.repositories.respositories;

import com.hard.qroyal.domain.entities.OrderDetail;
import com.hard.qroyal.infrastructure.repositories.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDetailRepository extends GenericRepository<OrderDetail> {

}

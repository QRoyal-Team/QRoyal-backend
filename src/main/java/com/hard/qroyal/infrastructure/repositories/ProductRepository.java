package com.hard.qroyal.infrastructure.repositories;

import com.hard.qroyal.domain.entities.Product;
import com.hard.qroyal.infrastructure.GenericRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends GenericRepository<Product> {

}

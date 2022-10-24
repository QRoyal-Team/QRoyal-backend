package com.hard.qroyal.infrastructure.services.commands;

import com.hard.qroyal.domain.entities.Product;
import com.hard.qroyal.infrastructure.services.BaseService;
import org.springframework.data.domain.Page;

public interface ProductService extends BaseService<Product> {

	public Page<Product> findAll(Integer size, Integer page, String sort, Boolean desc);
}

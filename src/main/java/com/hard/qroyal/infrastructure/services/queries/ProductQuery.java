package com.hard.qroyal.infrastructure.services.queries;

import com.hard.qroyal.domain.entities.Product;
import com.hard.qroyal.infrastructure.repositories.respositories.ProductRepository;
import com.hard.qroyal.infrastructure.services.BaseQuery;
import com.hard.qroyal.infrastructure.services.commands.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductQuery extends BaseQuery<Product, ProductRepository> implements ProductService {

	@Override
	public Page<Product> findAll(Integer size, Integer page) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Product> products = repository.findAll(pageable);
		return products;
	}
}

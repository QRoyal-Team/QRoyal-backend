package com.hard.qroyal.infrastructure.services.queries;

import com.hard.qroyal.domain.entities.Product;
import com.hard.qroyal.infrastructure.repositories.respositories.ProductRepository;
import com.hard.qroyal.infrastructure.services.BaseQuery;
import com.hard.qroyal.infrastructure.services.commands.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ProductQuery extends BaseQuery<Product, ProductRepository> implements ProductService {

	@Override
	public Page<Product> findAll(Integer size, Integer page, String sort, Boolean desc) {
		Pageable pageable;
		if (desc) {
			pageable = PageRequest.of(page, size, Sort.by(sort).descending());
		} else {
			pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
		}
		Page<Product> products = repository.findAll(pageable);
		return products;
	}
}

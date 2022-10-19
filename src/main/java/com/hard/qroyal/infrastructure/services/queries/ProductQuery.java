package com.hard.qroyal.infrastructure.services.queries;

import com.hard.qroyal.domain.entities.Product;
import com.hard.qroyal.infrastructure.repositories.ProductRepository;
import com.hard.qroyal.infrastructure.services.BaseQuery;
import com.hard.qroyal.infrastructure.services.commands.ProductService;
import org.springframework.stereotype.Service;

@Service
public class ProductQuery extends BaseQuery<Product, ProductRepository> implements ProductService {

}

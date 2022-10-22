package com.hard.qroyal.integration.controllers.client.product;

import com.hard.qroyal.domain.dtos.product.response.GetProductsResponse;
import com.hard.qroyal.domain.mappers.ProductMapper;
import com.hard.qroyal.infrastructure.services.commands.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/manager/product")
public class ProductController {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductMapper productMapper;

	@GetMapping("/getproducts")
	public Page<GetProductsResponse> getProducts(@RequestParam("size") Integer size,
			@RequestParam("page") Integer page) {
		Page<GetProductsResponse> getProductsResponses = productService.findAll(size, page)
				.map(productMapper::mapProductToGetProductsResponse);
		return getProductsResponses;
	}
	//	@GetMapping("/getproducts")
	//	public Page<Product> getProducts(@RequestParam("size") Integer size, @RequestParam("page") Integer page) {
	//		Page<Product> getProductsResponse = productService.findAll(size, page);
	//		return getProductsResponse;
	//	}
}

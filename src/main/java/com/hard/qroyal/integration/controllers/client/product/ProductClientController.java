package com.hard.qroyal.integration.controllers.client.product;

import com.hard.qroyal.domain.dtos.product.response.GetProductResponse;
import com.hard.qroyal.domain.mappers.ProductMapper;
import com.hard.qroyal.infrastructure.services.commands.ProductService;
import com.hard.qroyal.integration.BaseController;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/client/product")
public class ProductClientController extends BaseController<ProductService, ProductMapper> {

	@GetMapping("/gets")
	public Page<GetProductResponse> getProducts(@RequestParam("size") Integer size,
			@RequestParam("page") Integer page, @RequestParam("sort") String sort,
			@RequestParam("desc") Boolean desc) {
		Page<GetProductResponse> getProductsResponses = service.findAll(size, page, sort, desc)
				.map(mapper::mapProductToGetProductsResponse);
		return getProductsResponses;
	}

	@GetMapping("/get")
	public GetProductResponse getProducts(@RequestParam("id") Long id) {
		GetProductResponse getProductsResponse = mapper.mapProductToGetProductsResponse(service.findById(id));
		return getProductsResponse;
	}
}

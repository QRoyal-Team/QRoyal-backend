package com.hard.qroyal.integration.controllers.client.product;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hard.qroyal.domain.dtos.product.request.CreateProductRequest;
import com.hard.qroyal.domain.dtos.product.response.GetProductResponse;
import com.hard.qroyal.domain.entities.Product;
import com.hard.qroyal.domain.mappers.ProductMapper;
import com.hard.qroyal.infrastructure.services.commands.ProductService;
import com.hard.qroyal.integration.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/api/manager/product")
@Slf4j
public class ProductController extends BaseController<ProductService, ProductMapper> {

	@Autowired
	private Cloudinary cloudinary;

	@GetMapping("/getproducts")
	public Page<GetProductResponse> getProducts(@RequestParam("size") Integer size,
			@RequestParam("page") Integer page, @RequestParam("sort") String sort,
			@RequestParam("desc") Boolean desc) {
		Page<GetProductResponse> getProductsResponses = service.findAll(size, page, sort, desc)
				.map(mapper::mapProductToGetProductsResponse);
		return getProductsResponses;
	}

	@PostMapping(value = "/create")
	public GetProductResponse create(@Valid @RequestPart("product") CreateProductRequest createProductRequest,
			@RequestPart(value = "images", required = false) List<MultipartFile> images) {
		Product product = mapper.mapCreateProductRequestToProduct(createProductRequest);
		Product savedProduct = service.save(product);
		List<String> productImages = new ArrayList<>();
		AtomicInteger count = new AtomicInteger(0);
		images.forEach(image -> {
			try {
				Map resulf = cloudinary.uploader().upload(image.getBytes(), ObjectUtils.asMap("public_id",
						"images/product-" + savedProduct.getId() + "-" + count.getAndIncrement(), "overwrite",
						true, "resource_type", "auto"));
				productImages.add(resulf.get("secure_url").toString());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		savedProduct.setImages(productImages);
		service.save(savedProduct);
		return mapper.mapProductToGetProductsResponse(product);
	}
}

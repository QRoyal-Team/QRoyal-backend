package com.hard.qroyal.integration.controllers.manager.product;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.hard.qroyal.domain.dtos.product.request.CreateProductRequest;
import com.hard.qroyal.domain.dtos.product.request.UpdateProductRequest;
import com.hard.qroyal.domain.dtos.product.response.GetProductResponse;
import com.hard.qroyal.domain.entities.Product;
import com.hard.qroyal.domain.mappers.ProductMapper;
import com.hard.qroyal.infrastructure.services.commands.ProductService;
import com.hard.qroyal.integration.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.persistence.EntityNotFoundException;
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

	@PostMapping(value = "/update")
	public GetProductResponse update(@Valid @RequestPart("product") UpdateProductRequest updateProductRequest,
			@RequestPart(value = "images", required = false) List<MultipartFile> images) {
		Product product = mapper.mapUpdateProductRequestToProduct(updateProductRequest);
		Product updateProduct = service.findById(product.getId());
		if (updateProduct == null) {
			throw new EntityNotFoundException("Not found by id: " + product.getId());
		}
		List<String> productImages = new ArrayList<>();
		AtomicInteger count = new AtomicInteger(0);
		updateProductRequest.getImagesActions().forEach(imageAction -> {
			try {
				if (imageAction.getAction().equals("delete")) {
					cloudinary.uploader()
							.destroy("images/product-" + imageAction.getPublicId(), ObjectUtils.emptyMap());
				} else if (imageAction.getAction().equals("update")) {
					Map resulf = cloudinary.uploader().upload(images.get(count.get()).getBytes(),
							ObjectUtils.asMap("public_id", "images/product-" + imageAction.getPublicId(),
									"overwrite", true, "resource_type", "auto"));
					productImages.add(resulf.get("secure_url").toString());
				} else {
					productImages.add(updateProduct.getImages().get(count.get()));
				}
				count.incrementAndGet();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});
		updateProduct.setName(product.getName());
		updateProduct.setPrice(product.getPrice());
		updateProduct.setDiscount(product.getDiscount());
		updateProduct.setCatalog(product.getCatalog());
		updateProduct.setQuantity(product.getQuantity());
		updateProduct.setImages(productImages);
		product = service.save(updateProduct);
		return mapper.mapProductToGetProductsResponse(product);
	}

	@DeleteMapping("delete")
	public ResponseEntity<?> delete(@RequestParam(value = "id", required = true) Long id) {
		Product deleteProduct = service.findById(id);
		if (deleteProduct == null) {
			throw new EntityNotFoundException("Not found by id: " + id);
		}
		AtomicInteger count = new AtomicInteger(1);
		while (count.get() < 5) {
			try {
				cloudinary.uploader()
						.destroy("images/product-" + deleteProduct.getId() + "-" + count.getAndIncrement(),
								ObjectUtils.emptyMap());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		service.delete(deleteProduct);
		return ResponseEntity.ok("Deleted id:" + id);
	}
}

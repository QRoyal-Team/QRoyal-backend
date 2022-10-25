package com.hard.qroyal.domain.mappers;

import com.hard.qroyal.domain.BaseMapper;
import com.hard.qroyal.domain.dtos.product.request.CreateProductRequest;
import com.hard.qroyal.domain.dtos.product.request.UpdateProductRequest;
import com.hard.qroyal.domain.dtos.product.response.GetProductResponse;
import com.hard.qroyal.domain.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper extends BaseMapper {

	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

	@Mapping(target = "catalog.products", ignore = true)
	GetProductResponse mapProductToGetProductsResponse(Product product);

	@Mapping(target = "catalog.id", source = "catalogId")
	Product mapCreateProductRequestToProduct(CreateProductRequest createProductRequest);

	@Mapping(target = "catalog.id", source = "catalogId")
	Product mapUpdateProductRequestToProduct(UpdateProductRequest updateProductRequest);
}

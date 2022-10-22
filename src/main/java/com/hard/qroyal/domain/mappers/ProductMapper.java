package com.hard.qroyal.domain.mappers;

import com.hard.qroyal.domain.dtos.product.response.GetProductsResponse;
import com.hard.qroyal.domain.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ProductMapper {

	ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

	@Mapping(target = "catalog.products", ignore = true)
	GetProductsResponse mapProductToGetProductsResponse(Product product);
}

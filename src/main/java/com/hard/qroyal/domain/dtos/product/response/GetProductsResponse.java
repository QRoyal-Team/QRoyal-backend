package com.hard.qroyal.domain.dtos.product.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hard.qroyal.domain.BaseDTO;
import com.hard.qroyal.domain.entities.Catalog;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GetProductsResponse extends BaseDTO {

	private static final long serialVersionUID = 8429058852742073621L;

	@JsonProperty("name")
	private String name;

	@JsonProperty("quantity")
	private Integer quantity;

	@JsonProperty("price")
	private BigDecimal price;

	@JsonProperty("discount")
	private Integer discount;

	@JsonProperty("description")
	private String description;

	@JsonProperty("images")
	private List<String> images;

	@JsonProperty("created")
	private LocalDate created;

	@JsonProperty("catalog")
	private Catalog catalog;
}

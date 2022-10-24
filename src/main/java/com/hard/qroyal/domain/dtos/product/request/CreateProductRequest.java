package com.hard.qroyal.domain.dtos.product.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductRequest {

	@NotBlank
	@JsonProperty("name")
	private String name;

	@NotNull
	@JsonProperty("quantity")
	private Integer quantity;

	@NotNull
	@JsonProperty("price")
	private BigDecimal price;

	@JsonProperty("discount")
	private Integer discount;
	
	@JsonProperty("description")
	private String description;

	@NotNull
	@JsonProperty("catalog_id")
	private Long catalogId;
}

package com.hard.qroyal.domain.dtos.product.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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
	@Size(min = 0, max = 999)
	@JsonProperty("quantity")
	private Integer quantity;

	@NotNull
	@DecimalMin(value = "0.0", inclusive = false)
	@JsonProperty("price")
	private BigDecimal price;

	@Size(min = 0, max = 100)
	@JsonProperty("discount")
	private Integer discount;

	@JsonProperty("description")
	private String description;

	@NotNull
	@JsonProperty("catalog_id")
	private Long catalogId;
}

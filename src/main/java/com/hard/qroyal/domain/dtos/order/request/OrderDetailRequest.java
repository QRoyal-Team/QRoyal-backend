package com.hard.qroyal.domain.dtos.order.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailRequest {

	@NotNull
	@JsonProperty("product_id")
	private Long productId;

	@NotNull
	@JsonProperty("quantity")
	private Integer quantity;
}

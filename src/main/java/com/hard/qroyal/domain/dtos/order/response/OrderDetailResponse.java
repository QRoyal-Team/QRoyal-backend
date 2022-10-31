package com.hard.qroyal.domain.dtos.order.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {

	@JsonProperty("quantity")
	private Integer quantity;

	@JsonProperty("total")
	private BigDecimal total;
}

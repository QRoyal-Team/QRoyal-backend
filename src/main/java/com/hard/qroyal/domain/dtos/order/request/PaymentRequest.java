package com.hard.qroyal.domain.dtos.order.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PaymentRequest {

	@JsonProperty("bank_code")
	private String bankCode;

	@JsonProperty("order_info")
	private String orderInfo;

	@JsonProperty("return_url")
	private String returnUrl;
}

package com.hard.qroyal.domain.dtos.order.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderRequest {

	@JsonProperty("description")
	private String description;

	@NotNull
	@JsonProperty("name")
	private String name;

	@NotNull
	@JsonProperty("address")
	private String address;

	@NotNull
	@JsonProperty("phone")
	private String phone;

	@JsonProperty("order_details")
	private List<OrderDetailRequest> orderDetailRequests;

	@JsonProperty("payment")
	private PaymentRequest paymentRequest;
}

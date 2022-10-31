package com.hard.qroyal.domain.dtos.order.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponse {

	@JsonProperty("description")
	private String description;

	@JsonProperty("status")
	private String status = "Waiting Process";

	@JsonProperty("payment")
	private String payment;

	@JsonProperty("name")
	private String name;

	@JsonProperty("address")
	private String address;

	@JsonProperty("phone")
	private String phone;

	@JsonProperty("created")
	private LocalDateTime created;

	@JsonProperty("user_id")
	private Long userId;

	@JsonProperty("order_details")
	private List<OrderDetailResponse> orderDetailResponses;
}

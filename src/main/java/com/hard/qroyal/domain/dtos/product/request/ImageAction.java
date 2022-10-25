package com.hard.qroyal.domain.dtos.product.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ImageAction {

	@JsonProperty("public_id")
	private String publicId;

	@JsonProperty("action")
	private String action;
}
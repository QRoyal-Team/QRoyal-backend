package com.hard.qroyal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.io.Serializable;

@Data
public abstract class BaseDTO implements Serializable {

	private static final long serialVersionUID = 7849765802484073191L;

	@JsonProperty("id")
	private Long id;
}


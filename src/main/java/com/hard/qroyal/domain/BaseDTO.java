package com.hard.qroyal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
public abstract class BaseDTO implements Serializable {

	private static final long serialVersionUID = 7849765802484073191L;

	@JsonProperty("id")
	private Long id;

}


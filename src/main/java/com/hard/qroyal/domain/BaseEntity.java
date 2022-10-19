package com.hard.qroyal.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.*;
import java.io.Serializable;

@Inheritance
@MappedSuperclass
@Setter
@Getter
public abstract class BaseEntity implements Serializable {

	private static final long serialVersionUID = 5891103013003918892L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	@JsonProperty("id")
	private Long id;
}

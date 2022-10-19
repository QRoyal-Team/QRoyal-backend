package com.hard.qroyal.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hard.qroyal.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "catalog")
public class Catalog extends BaseEntity {

	private static final long serialVersionUID = 511603089603915269L;

	@JsonProperty("name")
	@Column(name = "name", length = 512, nullable = false)
	private String name;

	@JsonProperty("description")
	@Column(name = "description", length = 2048)
	private String description;

	@JsonProperty("products")
	@OneToMany(mappedBy = "catalog", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Product> products;
}

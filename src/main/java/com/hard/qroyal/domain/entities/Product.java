package com.hard.qroyal.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hard.qroyal.domain.BaseEntity;
import com.hard.qroyal.domain.converters.StringToListConverter;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "product")
public class Product extends BaseEntity {

	private static final long serialVersionUID = -4726797028132602885L;

	@JsonProperty("name")
	@Column(name = "name", length = 512, nullable = false)
	private String name;

	@JsonProperty("quantity")
	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@JsonProperty("price")
	@Column(name = "price", nullable = false)
	private BigDecimal price;

	@JsonProperty("discount")
	@Column(name = "discount")
	private Integer discount;

	@JsonProperty("description")
	@Column(name = "description", length = 2048, nullable = false)
	private String description;

	@JsonProperty("images")
	@Column(name = "images", length = 2048)
	@Convert(converter = StringToListConverter.class)
	private List<String> images;

	@JsonProperty("created")
	@Column(name = "created")
	private LocalDate created;

	@JsonProperty("catalog")
	@ManyToOne
	@JoinColumn(name = "catalog_id", referencedColumnName = "id")
	private Catalog catalog;

	@JsonProperty("order_details")
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<OrderDetail> orderDetails;

	@JsonProperty("reviews")
	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Review> reviews;
}

package com.hard.qroyal.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hard.qroyal.domain.BaseEntity;
import lombok.*;
import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "order_detail")
public class OrderDetail extends BaseEntity {

	private static final long serialVersionUID = -6721377613309938833L;

	@JsonProperty("quantity")
	@Column(name = "quantity", nullable = false)
	private Integer quantity;

	@JsonProperty("total")
	@Column(name = "total", nullable = false)
	private BigDecimal total;

	@JsonProperty("order")
	@ManyToOne
	@JoinColumn(name = "order_id", referencedColumnName = "id")
	private Order order;

	@JsonProperty("product")
	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;
}

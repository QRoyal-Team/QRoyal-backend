package com.hard.qroyal.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hard.qroyal.domain.BaseEntity;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "order")
public class Order extends BaseEntity {

	private static final long serialVersionUID = -7380825211581766898L;

	@JsonProperty("description")
	@Column(name = "description", length = 2048)
	private String description;

	@JsonProperty("status")
	@Column(name = "status", length = 100, nullable = false)
	private String status;

	@JsonProperty("name")
	@Column(name = "name", length = 100, nullable = false)
	private String name;

	@JsonProperty("address")
	@Column(name = "address", length = 256, nullable = false)
	private String address;

	@JsonProperty("phone")
	@Column(name = "phone", length = 50, nullable = false)
	private String phone;

	@JsonProperty("created")
	@Column(name = "created", nullable = false)
	private LocalDateTime created;

	@JsonProperty("user")
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@JsonProperty("order_details")
	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<OrderDetail> orderDetails;
}

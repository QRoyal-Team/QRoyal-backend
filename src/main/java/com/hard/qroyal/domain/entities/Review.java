package com.hard.qroyal.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hard.qroyal.domain.BaseEntity;
import com.hard.qroyal.domain.converters.StringToListConverter;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "review")
public class Review extends BaseEntity {

	private static final long serialVersionUID = 9102148760831116169L;

	@JsonProperty("rate")
	@Column(name = "rate")
	private Integer rate = 1;

	@JsonProperty("comment")
	@Column(name = "comment", length = 256)
	private String comment;

	@JsonProperty("images")
	@Column(name = "images", length = 256)
	@Convert(converter = StringToListConverter.class)
	private List<String> images;

	@JsonProperty("created")
	@Column(name = "created")
	private LocalDateTime created;

	@JsonProperty("product")
	@ManyToOne
	@JoinColumn(name = "product_id", referencedColumnName = "id")
	private Product product;

	@JsonProperty("user")
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
}

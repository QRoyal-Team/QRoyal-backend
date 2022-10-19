package com.hard.qroyal.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hard.qroyal.domain.BaseEntity;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "otp_message")
public class OtpMessage extends BaseEntity {

	private static final long serialVersionUID = -8076212975361734789L;

	@JsonProperty("random_code")
	@Column(name = "random_code", length = 100, nullable = false)
	private String randomCode;

	@JsonProperty("created")
	@Column(name = "created", nullable = false)
	private LocalDateTime created;

	@JsonProperty("expired")
	@Column(name = "expired", nullable = false)
	private LocalDateTime expired;

	@JsonProperty("order_detail")
	@OneToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;
}

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
@Table(name = "user")
public class User extends BaseEntity {

	private static final long serialVersionUID = 5294085563392348807L;

	@JsonProperty("name")
	@Column(name = "name", length = 512, nullable = false)
	private String name;

	@JsonProperty("username")
	@Column(name = "username", length = 256, nullable = false, unique = true)
	private String username;

	@JsonProperty("password")
	@Column(name = "password", length = 256, nullable = false)
	private String password;

	@JsonProperty("enabled")
	@Column(name = "enabled")
	private Boolean enabled;

	@JsonProperty("description")
	@Column(name = "description", length = 2048)
	private String description;

	@JsonProperty("role_assignments")
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<RoleAssignment> roleAssignments;

	@JsonProperty("orders")
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Order> orders;

	@JsonProperty("otp_message")
	@OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private OtpMessage otpMessage;

	@JsonProperty("reviews")
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<Review> reviews;
}

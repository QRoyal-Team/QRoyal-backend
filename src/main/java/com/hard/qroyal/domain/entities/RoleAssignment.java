package com.hard.qroyal.domain.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hard.qroyal.domain.BaseEntity;
import lombok.*;
import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "role_assignment")
public class RoleAssignment extends BaseEntity {

	private static final long serialVersionUID = 8774757267295443093L;

	@JsonProperty("user")
	@ManyToOne
	@JoinColumn(name = "user_id", referencedColumnName = "id")
	private User user;

	@JsonProperty("role")
	@ManyToOne
	@JoinColumn(name = "role_id", referencedColumnName = "id")
	private Role role;
}

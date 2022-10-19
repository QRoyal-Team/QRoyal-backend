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
@Table(name = "role")
public class Role extends BaseEntity {

	private static final long serialVersionUID = 1779774340718530369L;

	@JsonProperty("name")
	@Column(name = "name", length = 512, nullable = false)
	private String name;

	@JsonProperty("role_assignments")
	@OneToMany(mappedBy = "role", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<RoleAssignment> roleAssignments;
}

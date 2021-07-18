package warehouse.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "operators")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class Operator {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Exclude
	@Column(name = "operator_id")
	private long operatorId;
	@Setter
	@Column(name = "operator_name", unique = true, nullable = false)
	private String operatorName;
	@Setter
	@Column(name = "operator_email", nullable = false)
	private String operatorEmail;
	@Setter
	@Column(name = "username", unique = true)
	private String username;
	@Setter
	private String password;
	@Setter
	private LocalDate passwordExpiration;

	@Setter
	@OneToMany(mappedBy = "operator")
	@Column(name="operator_roles")
	private Set<Role> roles;


}

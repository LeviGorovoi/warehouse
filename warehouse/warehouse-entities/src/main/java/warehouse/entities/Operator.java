package warehouse.entities;

import java.util.Set;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "operators")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
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
	@Column(name = "operator_name", unique = true, nullable = false)
	private String operatorName;
	@Column(name = "operator_email", nullable = false)
	private String operatorEmail;

	@Setter
	@OneToMany(mappedBy = "operator")
	@Column(name="operator_roles")
	private Set<OperatorRole> operatorRoles;

	public Operator (String operatorName, String operatorEmail, Set<OperatorRole> operatorRoles) {
		this(0, operatorName, operatorEmail, operatorRoles);
	}
}

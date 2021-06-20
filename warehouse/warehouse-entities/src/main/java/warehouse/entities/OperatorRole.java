package warehouse.entities;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "operator_roles")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class OperatorRole {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Exclude
	@Column(name = "operator_role_id")
	private long operatorRoleId;
	@Setter
	private @Column(name = "role", nullable = false, unique = true)
	String role;
	@Setter
	@ManyToOne
	@JoinColumn(name="operator_id")
	private Operator operator;
	

}

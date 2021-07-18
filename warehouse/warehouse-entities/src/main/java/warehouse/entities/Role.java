package warehouse.entities;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "roles")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Exclude
	@Column(name = "role_id")
	private long roleId;
	@Setter
	private @Column(name = "role", nullable = false, unique = true)
	String role;
	@Setter
	@ManyToOne
	@JoinColumn(name="operator_id")
	private Operator operator;
	

}

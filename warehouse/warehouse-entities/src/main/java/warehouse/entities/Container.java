package warehouse.entities;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.*;

import lombok.*;


@Entity
@Table(name="container")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Container {
	@Id
	public long contanerId;
	@Column(name="address_id", nullable = false, unique = true)
	Address address;
	@Column(name="appointment_assignment_date", nullable = false)
	LocalDate appointmentAssignmentDate;
	@ManyToOne
	@JoinColumn(name = "product", nullable = false)
	Product productId;
}

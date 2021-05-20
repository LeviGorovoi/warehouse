package warehouse.entities;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.*;

@Entity
@IdClass(RemainderInContainerId.class)
@Table(name = "remainders_in_containers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class RemainderInContainer {
	@Id
	@ManyToOne
	@JoinColumn(name = "container_id")
	Container container;
	@Id
	@OneToOne
	@JoinColumn(name = "product_id")
	Product product;
	@Id
	LocalDateTime receiptDate;
	int remainder;
}

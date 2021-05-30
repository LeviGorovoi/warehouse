package warehouse.entities;

import java.time.LocalDateTime;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "remainders_in_containers",
indexes = @Index(columnList = "container_id, product_id, receipt_date_time", unique = true))
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@Builder
public class RemainderInContainer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Exclude
	@Column(name = "remainder_id")
	long remainderId;
	@ManyToOne
	@JoinColumn(name = "container_id")
	Container container;
	@ManyToOne
	@JoinColumn(name = "product_id")
	Product product;
	@Column(name = "receipt_date_time")
	LocalDateTime receiptDateTime;
	int remainder;
}

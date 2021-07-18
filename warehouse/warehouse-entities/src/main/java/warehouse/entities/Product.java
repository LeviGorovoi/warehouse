package warehouse.entities;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Exclude
	@Column(name = "product_id")
	private long productId;
	@Setter
	@Column(name="product_name", unique = true, nullable = false)
	private String productName;
	@Setter
	@Column(name = "number_in_container", nullable = false)
	private int numberInContainer;
	@Setter
	@Column(name = "transport_supply", nullable = true)
	private int transportSupply;
	@Setter
	@Column(name = "irreducible_balance", nullable = true)
	private int irreducibleBalance;
	
	
}

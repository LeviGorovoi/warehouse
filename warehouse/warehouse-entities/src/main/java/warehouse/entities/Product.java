package warehouse.entities;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Exclude
	@Column(name = "product_id")
	long productId;
	@Setter
	@Column(name="product_name", unique = true, nullable = false)
	String productName;
	@Setter
	@Column(name = "number_in_container", nullable = false)
	int numberInContainer;
	@Setter
	@Column(name = "transport_supply", nullable = true)
	int transportSupply;
	@Setter
	@Column(name = "irreducible_balance", nullable = true)
	int irreducibleBalance;
	
	public Product (String productName, int numberInContainer, int transportSupply, int irreducibleBalance) {
		this(0, productName, numberInContainer, transportSupply, irreducibleBalance);
	}
	
}

package warehouse.entities;

import javax.persistence.*;

import lombok.*;

@Entity
@Table(name = "products")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Product {
	@Id
	@Column(name = "product_id")
	public long productId;
	String productName;
	@Column(name = "number_in_container", nullable = false)
	int numberInContainer;
	@Column(name = "transport_stock", nullable = true)
	int transportStock;
	@Column(name = "irreducible_balance", nullable = true)
	int irreducibleBalance;
}

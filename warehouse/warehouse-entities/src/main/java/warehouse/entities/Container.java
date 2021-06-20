package warehouse.entities;



import javax.persistence.*;

import lombok.*;


@Entity
@Table(name="containers")
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class Container {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Exclude
 	@Column(name="container_id")
	private long containerId;
	@Setter
	@Column(name="address", unique =true, nullable = false)
	private String address;
	@Setter
	@ManyToOne
	@JoinColumn(name="product_id", nullable = true)
	private Product product;
	
	public Container(String address, Product product) {
		this(0, address, product);
	}
}

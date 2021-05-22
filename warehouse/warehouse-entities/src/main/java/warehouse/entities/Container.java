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
	@GeneratedValue
	@EqualsAndHashCode.Exclude
	@Column(name="container_id")
	long containerId;
	@Setter
	@Column(name="address", unique =true)
	String address;
	@Setter
	@ManyToOne
	@JoinColumn(name="product_id", nullable = true)
	Product product;
	
	public Container(String address, Product product) {
		this(0, address, product);
	}
}

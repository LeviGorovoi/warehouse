package warehouse.entities;



import javax.persistence.*;

import lombok.*;


@Entity
@Table(name="containers")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Container {
	@Id
	@Column(name="container_id")
	public long containerId;
	String address;
	@ManyToOne
	@Column(name="product_id", nullable = true)
	Product product;
	
}

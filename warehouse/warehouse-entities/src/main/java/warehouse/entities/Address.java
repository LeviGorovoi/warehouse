package warehouse.entities;

import javax.persistence.*;

import lombok.*;


@Entity
@Table(name="address")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class Address {
@Id
long addressId;
@Column(name="address_id", nullable = false, unique = true)
public String address;
}

package warehouse.dto;

import java.util.Date;

import javax.validation.constraints.*;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ContainerDto {
	@Min(1)
	public long contanerId;
	@Min(1)
	public long addressId;
	@NotNull
	public Date appointmentAssignmentDate;
	@Min(1)
	public long productId;
}

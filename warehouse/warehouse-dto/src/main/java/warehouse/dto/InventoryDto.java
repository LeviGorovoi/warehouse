package warehouse.dto;


import javax.validation.constraints.Min;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class InventoryDto {
	@Min(1)
	public long containerId;
	@NotEquals(value = 5, message = "must not be equal to {value}")
	public int deviation;

}

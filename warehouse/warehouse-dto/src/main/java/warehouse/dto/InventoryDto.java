package warehouse.dto;


import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
	@Min(1)
	public long productId;
	@NotNull
	public Date receiptDate;
	@NotEquals(value = 0, message = "must not be equal to {value}")
	public int deviation;


}

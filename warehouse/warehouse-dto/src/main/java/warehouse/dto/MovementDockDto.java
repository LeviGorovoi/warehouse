package warehouse.dto;


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
public class MovementDockDto {
	@Min(1)
	public long productId;
	@Min(1)
	public int movementDockAmount;
	@NotNull
	public  CreatingMethodEnum creatingMethod;
	@NotNull
	public DockTypeEnum dockType;
	

}

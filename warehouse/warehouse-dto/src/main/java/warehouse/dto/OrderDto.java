package warehouse.dto;

import javax.validation.constraints.*;
import lombok.*;
import warehouse.dto.enums.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class OrderDto {
	@Min(1)
	public long productId;
	@Min(1)
	public int orderAmount;
	@NotNull
	public OrderStatusEnum orderStatus ;
}

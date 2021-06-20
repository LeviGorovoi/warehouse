package warehouse.dto.product;

import java.util.Date;
import javax.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class TransportSupplySettingDto {
	@FutureOrPresent
	@NotNull
	public Date transportSupplySettingDate;
	@Min(1)
	public long productId;
	public int transportSupply; // in days

}

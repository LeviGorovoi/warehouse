package warehouse.dto.product;

import java.util.Date;
import javax.validation.constraints.*;
import lombok.*;
import warehouse.dto.ParentDto;

@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@Setter
@Getter
public class TransportSupplySettingDto extends ParentDto {
	@FutureOrPresent
	@NotNull
	@EqualsAndHashCode.Exclude
	private Date settingDate;
	@Min(1)
	private long productId;
	private int transportSupply; // in days
}

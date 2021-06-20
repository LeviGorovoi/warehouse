package warehouse.dto.role;

import java.util.Date;
import javax.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OperatorToRoleSettingDto {
	@FutureOrPresent
	@NotNull
	public Date operatorToRoleSettingDate;
	@Min(1)
	public long operatorRoleId;
	@Min(1)
	public long operatorId;

	
}

package warehouse.dto.operator;

import javax.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ChangeOperatorNameDto {
	@Min(1)
	public long operatorId;
	@NotEmpty
	String newOperatorName;
}

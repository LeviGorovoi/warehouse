package warehouse.dto.operator;

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
public class ChangeOperatorNameDto extends ParentDto {
	@Min(1)
	private long operatorId;
	@NotEmpty
	private String newOperatorName;
}

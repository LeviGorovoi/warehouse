package warehouse.dto.operator;

import javax.validation.constraints.*;
import lombok.*;
import warehouse.dto.ParentDto;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@Builder
@Setter
@Getter
public class ChangeOperatorEmailDto extends ParentDto {
	@Min(1)
	private long operatorId;
	@NotEmpty
	private String newOperatorEmail;
	private long executorOperatorId;
}

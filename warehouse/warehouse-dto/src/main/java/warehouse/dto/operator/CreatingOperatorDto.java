package warehouse.dto.operator;

import javax.validation.constraints.NotEmpty;

import lombok.*;
import warehouse.dto.ParentDto;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@Builder
@Setter
@Getter
public class CreatingOperatorDto extends ParentDto {
	@NotEmpty
	private String operatorName;
	@NotEmpty
	private String operatorEmail;
	private long executorOperatorId;
}

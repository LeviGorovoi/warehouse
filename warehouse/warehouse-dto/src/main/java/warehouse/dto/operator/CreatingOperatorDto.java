package warehouse.dto.operator;

import javax.validation.constraints.NotEmpty;

import lombok.*;
import warehouse.dto.ParentDto;

@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@Setter
@Getter
public class CreatingOperatorDto extends ParentDto {
	@NotEmpty
	private String operatorName;
	@NotEmpty
	private String operatorEmail;
}

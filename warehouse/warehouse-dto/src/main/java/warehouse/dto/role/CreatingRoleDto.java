package warehouse.dto.role;

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
public class CreatingRoleDto extends ParentDto {
	@NotEmpty
	private String role;
	private long executorOperatorId;
}

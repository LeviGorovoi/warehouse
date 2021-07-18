package warehouse.dto.role;

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
public class ChangeRoleDto extends ParentDto {
	@Min(1)
	private long roleId;
	@NotEmpty
	private String newRole;
	private long executorOperatorId;
}

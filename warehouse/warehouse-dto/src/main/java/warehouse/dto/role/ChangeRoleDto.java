package warehouse.dto.role;

import javax.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ChangeRoleDto {
	@Min(1)
	public long operatorRoleId;
	@NotEmpty
	String newRole;
}

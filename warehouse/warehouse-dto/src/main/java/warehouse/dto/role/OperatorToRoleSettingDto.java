package warehouse.dto.role;

import java.util.Date;
import javax.validation.constraints.*;

import lombok.*;
import warehouse.dto.*;


@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
@Builder
@Setter
@Getter
public class OperatorToRoleSettingDto extends ParentDto {
	@FutureOrPresent
	@NotNull
	@EqualsAndHashCode.Exclude
	private Date settingDate;
	@Min(1)
	private long roleId;
	@Min(1)
	private long operatorId;
}

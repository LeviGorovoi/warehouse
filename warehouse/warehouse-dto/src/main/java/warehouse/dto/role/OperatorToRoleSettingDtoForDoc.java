package warehouse.dto.role;

import java.util.Date;
import javax.validation.constraints.*;
import lombok.*;
import warehouse.dto.DocumentDtosParent;
import warehouse.dto.enums.CreatingMethodEnum;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OperatorToRoleSettingDtoForDoc extends DocumentDtosParent {
	public Date operatorToRoleSettingDate;
	public long operatorRoleId;
	public long operatorId;
	public CreatingMethodEnum creatingMethod;
	public int operatorManagerId;
	
}

package warehouse.dto.operator;

import javax.validation.constraints.NotEmpty;

public class CreatingOperatorDto {
	@NotEmpty
	public String operatorName;
	@NotEmpty
	public String operatorEmail;
}

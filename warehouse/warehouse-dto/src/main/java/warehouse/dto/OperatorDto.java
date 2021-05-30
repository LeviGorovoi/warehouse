package warehouse.dto;

import javax.validation.constraints.NotEmpty;

public class OperatorDto {
	@NotEmpty
	String operatorName;
	String operatorEmail;
}

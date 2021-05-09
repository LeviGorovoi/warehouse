package warehouse.dto;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class IrreducibleBalanceDto {
	@NotNull
	public Date irreducibleBalanceAppointmentAssignmentDate;
	@Min(1)
	public long productId;
	@Min(0)
	public int irreducibleBalanceAmount;
	@NotNull
	public  CreatingMethodEnum creatingMethod;
	
}

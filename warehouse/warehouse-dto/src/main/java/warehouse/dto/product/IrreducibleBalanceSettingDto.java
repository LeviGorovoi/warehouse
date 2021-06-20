package warehouse.dto.product;

import java.util.Date;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class IrreducibleBalanceSettingDto {
	@FutureOrPresent
	@NotNull
	public Date irreducibleBalanceSettingDate;
	@Min(1)
	public long productId;
	@Min(0)
	public int irreducibleBalanceAmount;

	
}

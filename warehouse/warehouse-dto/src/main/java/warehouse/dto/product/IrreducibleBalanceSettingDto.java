package warehouse.dto.product;

import java.util.Date;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import warehouse.dto.ParentDto;

@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@Setter
@Getter
public class IrreducibleBalanceSettingDto extends ParentDto {
	@FutureOrPresent
	@NotNull
	@EqualsAndHashCode.Exclude
	private Date settingDate;
	@Min(1)
	private long productId;
	@Min(0)
	private int irreducibleBalance;	
}

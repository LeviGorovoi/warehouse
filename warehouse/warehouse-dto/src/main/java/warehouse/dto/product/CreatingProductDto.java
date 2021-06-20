package warehouse.dto.product;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CreatingProductDto {
	@NotEmpty
	public String productName;
	@Min(1)
	public int  numberInContainer;
}

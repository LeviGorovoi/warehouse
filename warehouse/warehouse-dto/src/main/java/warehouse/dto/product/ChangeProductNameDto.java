package warehouse.dto.product;

import javax.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ChangeProductNameDto {
	@Min(1)
	public long productId;
	@NotEmpty
	String newProductName;
}

package warehouse.dto.product;

import javax.validation.constraints.*;
import lombok.*;
import warehouse.dto.ParentDto;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
@Builder
@Setter
@Getter
public class ChangeProductNameDto extends ParentDto {
	@Min(1)
	private long productId;
	@NotEmpty
	private String newProductName;
	private long executorOperatorId;
}

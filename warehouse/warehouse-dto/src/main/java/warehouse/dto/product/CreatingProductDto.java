package warehouse.dto.product;

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
public class CreatingProductDto extends ParentDto {
	@NotEmpty
	private String productName;
	@Min(1)
	private int numberInContainer;
}

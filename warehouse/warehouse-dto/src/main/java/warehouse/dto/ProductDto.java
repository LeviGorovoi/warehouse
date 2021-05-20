package warehouse.dto;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductDto {
	@Min(1)
	public long productId;
	@NotNull
	public String productName;
	@Min(1)
	public int  numberInContainer;
}
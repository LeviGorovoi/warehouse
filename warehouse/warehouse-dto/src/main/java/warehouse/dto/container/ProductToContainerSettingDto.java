package warehouse.dto.container;

import java.util.Date;
import javax.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductToContainerSettingDto {
	@FutureOrPresent
	@NotNull
	public Date productToContainerSettingDate;
	@Min(1)
	public long containerId;
	@Min(1)
	public long productId;


	
}

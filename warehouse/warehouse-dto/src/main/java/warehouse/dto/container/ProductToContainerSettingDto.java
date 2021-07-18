package warehouse.dto.container;

import java.util.Date;
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
public class ProductToContainerSettingDto extends ParentDto {
	@FutureOrPresent
	@NotNull
	@EqualsAndHashCode.Exclude
	private Date settingDate;
	@Min(1)
	private long containerId;
	@Min(1)
	private long productId;
	private long executorOperatorId;

}

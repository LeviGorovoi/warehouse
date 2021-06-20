package warehouse.dto.container;

import java.util.Date;
import javax.validation.constraints.*;

import org.springframework.boot.jackson.JsonComponent;

import lombok.*;
import warehouse.dto.DocumentDtosParent;
import warehouse.dto.enums.CreatingMethodEnum;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
public class ProductToContainerSettingDtoForDoc extends DocumentDtosParent{
	public Date productToContainerSettingDate;
	public long containerId;
	public long productId;
	public CreatingMethodEnum creatingMethod;
	public int operatorId;
	
}

package warehouse.dto.container;

import javax.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ChangeContainerAddressDto {
@Min(1)
public long containerId;
@NotEmpty
String newAddress;
}

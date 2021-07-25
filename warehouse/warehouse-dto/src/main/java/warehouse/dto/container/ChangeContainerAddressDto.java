package warehouse.dto.container;


import javax.validation.constraints.*;
import lombok.*;
import warehouse.dto.ParentDto;

@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper=true)
@EqualsAndHashCode(callSuper=true)
@Builder
@Setter
@Getter
public class ChangeContainerAddressDto extends ParentDto {
@Min(1)
private long containerId;
@NotEmpty
private String newAddress;
}

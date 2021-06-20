package warehouse.dto.container;


import javax.validation.constraints.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class CreatingContainerDto {
	@NotEmpty
	public String address;
}

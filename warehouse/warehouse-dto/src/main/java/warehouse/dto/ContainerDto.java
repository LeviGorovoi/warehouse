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
public class ContainerDto {

	@NotNull
	public String address;
}

package warehouse.dto.container;


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
public class CreatingContainerDto extends ParentDto {
	@NotEmpty
	private String address;
	private long executorOperatorId;
}

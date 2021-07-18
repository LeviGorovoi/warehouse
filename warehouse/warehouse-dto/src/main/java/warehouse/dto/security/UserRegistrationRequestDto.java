package warehouse.dto.security;

import javax.validation.constraints.NotEmpty;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
@Builder
public class UserRegistrationRequestDto {
	@NotEmpty
	private String operatorName;
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
	private long executorOperatorId;

}

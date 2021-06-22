package warehouse.dto.security;

import javax.validation.constraints.NotEmpty;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@Builder
public class UserRegistrationRequestDto {
	@NotEmpty
public String operatorName;
	@NotEmpty
public String userName;
	@NotEmpty
public String password;
	
}

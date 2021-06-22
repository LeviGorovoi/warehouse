package warehouse.dto.security;

import javax.validation.constraints.NotEmpty;

public class UserLoginRequestDto {
	@NotEmpty
	public String username;
	@NotEmpty
	public String password;
}

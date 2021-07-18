package warehouse.dto.security;



import javax.validation.constraints.NotEmpty;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class UserLoginRequestDto {
	@NotEmpty
	private String username;
	@NotEmpty
	private String password;
}

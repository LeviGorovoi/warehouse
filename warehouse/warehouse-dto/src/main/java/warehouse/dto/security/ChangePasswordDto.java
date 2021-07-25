package warehouse.dto.security;


import javax.validation.constraints.NotEmpty;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class ChangePasswordDto {
	@NotEmpty
	private String oldPassword;
	@NotEmpty
	private String newPassword;
}

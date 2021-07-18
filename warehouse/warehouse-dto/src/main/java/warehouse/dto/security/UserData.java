package warehouse.dto.security;


import java.time.LocalDate;

import lombok.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
@Builder
public class UserData  {
String username;
String password;
@Setter
LocalDate passwordExpiration;
String[] roles;
}

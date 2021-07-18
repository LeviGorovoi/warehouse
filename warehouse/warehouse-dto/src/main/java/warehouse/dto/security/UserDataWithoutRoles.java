package warehouse.dto.security;

import java.time.LocalDate;



public interface UserDataWithoutRoles {
 String getUsername();
 String getPassword();
 LocalDate getPasswordExpiration();
}

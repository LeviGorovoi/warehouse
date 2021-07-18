package warehouse.service.interfaces;




import reactor.core.publisher.Mono;
import warehouse.dto.security.*;

public interface SecurityService {
	Mono<String> register(UserRegistrationRequestDto u);

	Mono<String> login(UserLoginRequestDto loginRequest);

	Mono<UserData> getUserData(String username);

	Mono<String> changePassword(String password, String newPassword, String username);


}

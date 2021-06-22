package warehouse.service.interfaces;

import javax.validation.constraints.NotEmpty;

import reactor.core.publisher.Mono;
import warehouse.dto.security.*;

public interface SecurityService {
	Mono<Void> register(UserRegistrationRequestDto u);

	Mono<UserData> getByUsername(String username);

	Mono<String> login(UserLoginRequestDto loginRequest);

//	void makeRegistration(@NotEmpty String operatorName, @NotEmpty String userName, @NotEmpty String password);
}

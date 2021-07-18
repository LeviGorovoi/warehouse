package warehouse.controllers;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import warehouse.dto.security.*;

import static warehouse.dto.api.SecurityApi.*;

import warehouse.service.interfaces.SecurityService;

@RestController
@RequestMapping(SECURITY)
@Slf4j
public class AccountingController {
	private SecurityService securityService;

	public AccountingController(SecurityService securityService) {
		this.securityService = securityService;
	}

	@PostMapping(REGISTER)
	public Mono<String> register(@Valid @RequestBody UserRegistrationRequestDto userRegistrationRequestDto) {
		log.debug("the userRegistrationRequestDto {}", userRegistrationRequestDto.getOperatorName());
		return securityService.register(userRegistrationRequestDto);
	}

	@PostMapping(PASSWORD_CHANGE)
	public Mono<String> changePassword(@Valid @RequestBody ChangePasswordDto changePasswordDto, Principal currentUsername) {
		return securityService.changePassword(changePasswordDto.getOldPassword(), changePasswordDto.getNewPassword(), currentUsername.getName());
	}

	@PostMapping(LOGIN)
	public Mono<String> login(@Valid @RequestBody UserLoginRequestDto loginRequest) {
		return securityService.login(loginRequest);
	}

	@GetMapping(USER_DATA)
	public Mono<UserData> getUserData(String username) {
		return securityService.getUserData(username);
	}

}

package warehouse.service.impl;

import java.time.LocalDate;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import warehouse.configuration.SecurityConfiguration;
import warehouse.dto.security.*;
import warehouse.exceptions.*;
import warehouse.jwt.JWTConverter;
import warehouse.repo.OperatorSecurityRepo;
import warehouse.service.interfaces.SecurityService;

@Service(value = "ss")
@Slf4j
public class SecurityServiceImpl implements SecurityService {
	@Value("${passwod-validity-period:60}")
	int passwordValidityPeriod;// days
	@Value("${app-binding-name-docs:docs-out-0}")
	String dtoForDocBindingName;
	@Autowired
	OperatorSecurityRepo operatorRepo;
	@Resource(name = "ss")
	SecurityServiceImpl serviceImpl;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	JWTConverter jwtConverter;
	@Autowired
	SecurityConfiguration securityConfiguration;

	

	
	@Transactional
	public void makeRegistration(String operatorName, String username, String password) {
		if (operatorRepo.register(operatorName, username, password,
				LocalDate.now().plusDays(passwordValidityPeriod)) == 0) {
			throw new NotFoundException(String.format("operator %s not found", operatorName));
		}
	}

	@Override
	public Mono<String> register(UserRegistrationRequestDto userRegistrationRequestDto) {
		log.debug("the userRegistrationRequestDto {} recieved", userRegistrationRequestDto.getOperatorName());
		return Mono.create(s -> {
			serviceImpl.makeRegistration(userRegistrationRequestDto.getOperatorName(), userRegistrationRequestDto.getUsername(),
					passwordEncoder.encode(userRegistrationRequestDto.getPassword()));
			s.success(String.format("The username %s is registered successfully", userRegistrationRequestDto.getUsername()));
		});
	}

	@Transactional
	public void makeUpdatingePassword(String username, String newPassword) {
		if (operatorRepo.updatePassword(username, passwordEncoder.encode(newPassword),
				LocalDate.now().plusDays(passwordValidityPeriod)) == 0) {
			throw new NotFoundException(String.format("username %s not found", username));
		}
	}

	@Override
	public Mono<String> changePassword(String password, String newPassword, String username) {
		return Mono.defer(() -> {
			String referencePassword = operatorRepo.findPasswordByUsername(username);
			log.debug("recieved from DB password {} by user {}", referencePassword, username);

			return checkIfValidPassword(password, referencePassword, new String("The password changed successfully"),
					"Invalid oldPassword").doOnError(e -> {
						log.debug("changePassword: exception {}, {}", e.getClass(), e.getMessage());
						e.getMessage();
					}).doOnSuccess(t -> {
						serviceImpl.makeUpdatingePassword(username, newPassword);
						log.debug(t);
					});

		});
	}

	private <T> Mono<T> checkIfValidPassword(String password, String referencePassword, T obj, String errorMessage) {
		return passwordEncoder.matches(password, referencePassword) ? Mono.just(obj)
				: Mono.error(new InvalidCredentialException(errorMessage));
	}

	public UserDataWithoutRoles getUserByUsername(String username) {
//		UserData will not include a set of roles. It is retrieved from the database separately.	
		UserDataWithoutRoles user = operatorRepo.getUserData(username);
		if (user == null) {
			throw new NotFoundException(String.format("username %s not found", username));
		}
		log.debug("got user with username {} by thread {}", user.getUsername(), Thread.currentThread().getName());
		return user;
	}

	public String[] getRolesByUsername(String username) {
		log.debug("the username {} recieved by thread {} ", username, Thread.currentThread().getName());

		String[] operatorRoles = operatorRepo.getRoles(username);
		if (operatorRoles == null) {
			throw new NotFoundException(String.format("username %s not found", username));
		}
		log.debug("got roles {} by thread {}", List.of(operatorRoles).toString(), Thread.currentThread().getName());
		return operatorRoles;
	}

	@Override
	public Mono<UserData> getUserData(String username) {
		log.debug("the username {} recieved by thread {} ", username, Thread.currentThread().getName());
		if (username.equalsIgnoreCase("admin")) {
			return Mono.defer(() -> {
				UserData admin = securityConfiguration.createUsersInMemory().get(username);
				admin.setPasswordExpiration(LocalDate.now().plusDays(passwordValidityPeriod));
				return Mono.just(admin);
			});
		}
		return Mono.defer(() -> {
			UserDataWithoutRoles user = getUserByUsername(username);
			LocalDate passwordExpiration = user.getPasswordExpiration();
			if (passwordExpiration.isBefore(LocalDate.now())) {
				throw new PasswordExpiredException(
						"Your password has expired. Contact the administrator for re-registration");
			}
			String[] roles = getRolesByUsername(username);
			UserData userData = UserData.builder().roles(roles).password(user.getPassword())
					.passwordExpiration(user.getPasswordExpiration()).username(user.getUsername()).build();
			return Mono.just(userData);
		});

	}

	@Override
	public Mono<String> login(UserLoginRequestDto loginRequest) {
		return getUserData(loginRequest.getUsername()).flatMap(userData -> {
			UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userData.getUsername(),
					null, AuthorityUtils.createAuthorityList(userData.getRoles()));
			return checkIfValidPassword(loginRequest.getPassword(), userData.getPassword(), token,
					"Invalid username or password");
		}).map(jwtConverter::authenticationToToken);
	}

}

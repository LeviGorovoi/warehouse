package warehouse.service.impl;

import java.time.LocalDate;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import warehouse.dto.security.UserData;
import warehouse.dto.security.UserLoginRequestDto;
import warehouse.dto.security.UserRegistrationRequestDto;
import warehouse.entities.Operator;
import warehouse.exceptions.NotFoundException;
import warehouse.repo.OperatorSecurityRepo;
import warehouse.service.interfaces.SecurityService;

@Service(value = "ss")
@Slf4j
public class SecurityServiceImpl implements SecurityService {
	@Value("${passwod-validity-period}")
	int passwordValidityPeriod;//days
	@Value("${app-binding-name-docs:docs-out-0}")
	String dtoForDocBindingName;
	@Autowired
	OperatorSecurityRepo operatorSecurityRepo;
	@Resource(name = "ss")
	SecurityServiceImpl sserviceImpl;
	@Autowired
	PasswordEncoder passwordEncoder;


	@Transactional
	public void makeRegistration (String operatorName, String userName, String password) {
		try {
			if(operatorSecurityRepo.register(operatorName, userName, password, LocalDate.now().plusDays(passwordValidityPeriod))==0) {
				throw new NotFoundException(String.format("operator %s not found", operatorName));
			};
		} catch (Exception e) {
			log.debug("exception {} {}", e.getClass(), e.getMessage());
			
		}
	}
	
	@Override
	public Mono<Void> register(UserRegistrationRequestDto userRegistrationRequestDto) {
		log.debug("the userRegistrationRequestDto {} recieved by thread {} ", userRegistrationRequestDto.operatorName, Thread.currentThread().getName());
		return Mono.create(s -> {
			sserviceImpl.makeRegistration(userRegistrationRequestDto.operatorName, userRegistrationRequestDto.userName,
					passwordEncoder.encode(userRegistrationRequestDto.password));
		s.success();
		});
	}
//	@Override
//	@Transactional
//	public void register(UserRegistrationRequestDto userRegistrationRequestDto) {
//		try {
//			operatorSecurityRepo.register(userRegistrationRequestDto.operatorName, userRegistrationRequestDto.userName,
//					passwordEncoder.encode(userRegistrationRequestDto.password));
//		} catch (Exception e) {
//			log.debug("exception {} {}", e.getClass(), e.getMessage());
//			throw new NotFoundException(
//					String.format("operator %s not found", userRegistrationRequestDto.operatorName));
//		}
//	}

	@Override
	@Transactional
	public Mono<UserData> getByUsername(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Mono<String> login(UserLoginRequestDto loginRequest) {
		// TODO Auto-generated method stub
		return null;
	}

}

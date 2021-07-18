package warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//import static warehouse.dto.api.SecurityApi.PASSWORD_CHANGE;
//import static warehouse.dto.api.SecurityApi.SECURITY;
//import static warehouse.dto.api.WarehouseConfiguratorApi.*;

import java.io.IOException;


import warehouse.service.interfaces.SecurityService;
//import warehouse.dto.JsonForJournalingDto;
import warehouse.dto.security.*;
import warehouse.entities.*;
import warehouse.exceptions.InvalidCredentialException;
import warehouse.exceptions.NotFoundException;
import warehouse.repo.*;

@SpringBootTest
@AutoConfigureWebTestClient
@AutoConfigureDataJpa
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
//@Import(TestChannelBinderConfiguration.class)
public class GatewayTests {

	private static final String OPERATOR_NAME = "Moishe1";

	private static final String USERNAME = "moshe@gmail.com";

	private static final String PASSWORD = "Moishe123";

//	@Value("${app-binding-name-docs:docs-out-0}")
//	String dtoForDocBindingName;

//	ObjectMapper mapper = new ObjectMapper();
	@Autowired
	WebTestClient testClient;
	@Autowired
	SecurityService service;
	@Autowired
	OperatorSecurityRepo operatorSecurityRepo;
	@Autowired
	RoleSecurityRepo roleSecurityRepo;
//	@Autowired
//	OutputDestination consumer;
	@Autowired
	PasswordEncoder passwordEncoder;
	
@AllArgsConstructor
  class updatePasswordDto {
	  String oldPasword;
	  String newPasword;
  }

//	private <T> void normalPostTest (String uriStr, Object requestObj, T responseObj, Class<T> responseClass) {
//		testClient.post().uri(uriStr)
//		.contentType(MediaType.APPLICATION_JSON).bodyValue(requestObj).exchange().expectStatus().isOk()
//		.expectBody(responseClass).isEqualTo( responseObj);
//	}


	@Test
	@Sql("fillTables.sql")
	@Order(1)
	void registerTest() throws IOException {
		UserRegistrationRequestDto userRegistrationRequestDto = UserRegistrationRequestDto.builder()
				.operatorName(OPERATOR_NAME).username(USERNAME).password(PASSWORD).build();
		service.register(userRegistrationRequestDto).block();

		Operator operator = operatorSecurityRepo.findByOperatorName(OPERATOR_NAME);
		log.debug("registerTest: operatorId: {}", operator.getOperatorId());
		assertEquals(OPERATOR_NAME, operator.getOperatorName());
		assertEquals(USERNAME, operator.getUsername());
		assertFalse(operator.getPassword().equals(""));
		log.debug("registerTest: Saved password: {}", operator.getPassword());
		log.debug("registerTest: password expiration: {}", operator.getPasswordExpiration());
		
		try {
			userRegistrationRequestDto = UserRegistrationRequestDto.builder().operatorName(OPERATOR_NAME + "1")
					.username(USERNAME).password(PASSWORD).build();
			service.register(userRegistrationRequestDto).block();
			log.debug("registerTest: found by name {}", OPERATOR_NAME + "1");
		} catch (NotFoundException e) {
			log.debug("registerTest: Exception {}, operator {} not found", e.getClass(), OPERATOR_NAME + "1");
		}

//		Message<byte[]> message = consumer.receive(0, dtoForDocBindingName);
//		String dtoForDocJson = new String(message.getPayload());
//		System.out.println("++++++++++++++++++++++++"+dtoForDocJson);
//		ProductToContainerSettingDtoForDoc dtoForDoc  = (ProductToContainerSettingDtoForDoc) mapper.readValue(dtoForDocJson, DocumentDtosParent.class);
//		ProductToContainerSettingDtoForDoc dtoForDocExp = ProductToContainerSettingDtoForDoc.builder()
//				.productToContainerSettingDate(null)
//				.containerId(1)
//				.productId(1)
//				.creatingMethod(null)
//				.operatorId(0).build();
//		assertEquals(dtoForDoc, dtoForDocExp);
	}

	@Test
	@Order(2)
	void getUserDataTest() {
		UserData user = service.getUserData(USERNAME).block();
		log.debug("getUserDataTest: password from userdata: {}", user.getPassword());
		log.debug("getUserDataTest: password expiration: {}", user.getPasswordExpiration());
		assertEquals(2, user.getRoles().length);
		assertEquals("role1", user.getRoles()[0]);
		assertEquals("role2", user.getRoles()[1]);
		assertThrows(NotFoundException.class, () -> service.getUserData(USERNAME + "1").block());

	}
	
//	@Test
//	@Order(3)
//	void checkIfValidPasswordTest() throws IOException {
//		String referencePassword = operatorSecurityRepo.findPasswordByUsername(USERNAME);
//		log.debug("checkIfValidPasswordTest: referencePassword: {}", operatorSecurityRepo.findPasswordByUsername(USERNAME));
//		String res = service.checkIfValidPassword(PASSWORD, referencePassword, new String("test")).block();
//assertEquals("test", res);
//	}
	@Test
	@Order(3)
	void updatePasswordTest() throws IOException {
		String oldPassword = operatorSecurityRepo.findPasswordByUsername(USERNAME);
		log.debug("updatePasswordTest: old password: {}", oldPassword);
		service.changePassword(PASSWORD, PASSWORD + "2", USERNAME).block();
		String newPassword = operatorSecurityRepo.findPasswordByUsername(USERNAME);
		log.debug("updatePasswordTest: new password1: {}", newPassword);
assertTrue(passwordEncoder.matches(PASSWORD + "2", newPassword));
		
		try {
			service.changePassword(PASSWORD + "3", PASSWORD + "1", USERNAME).block();
			newPassword = operatorSecurityRepo.findPasswordByUsername(USERNAME);
			log.debug("updatePasswordTest: new password2: {}", newPassword);
			assertTrue(passwordEncoder.matches(PASSWORD + "1", newPassword));
		} catch (InvalidCredentialException e) {
			log.debug("updatePasswordTest: Exception {}, passvord {} wrong", e.getClass(), PASSWORD + "3");
		}
	}
	
	
	//+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
}

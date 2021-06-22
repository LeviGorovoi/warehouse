package warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.messaging.Message;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import static warehouse.dto.api.WarehouseConfiguratorApi.*;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotEmpty;


import warehouse.service.interfaces.SecurityService;
import warehouse.dto.DocumentDtosParent;
import warehouse.dto.security.UserRegistrationRequestDto;
import warehouse.entities.*;
import warehouse.repo.OperatorSecurityRepo;

@SpringBootTest
@AutoConfigureWebTestClient
@AutoConfigureDataJpa
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(TestChannelBinderConfiguration.class)
public class SecurityTests {

private static final String OPERATOR_NAME = "Moishe1";

private static final String USER_NAME = "moshe@gmail.com";

private static final String PASSWORD = "Moishe123";

@Value("${app-binding-name-docs:docs-out-0}")
String dtoForDocBindingName;

	ObjectMapper mapper = new ObjectMapper();
	@Autowired
	WebTestClient testClient;
	@Autowired
	SecurityService service;
	@Autowired
	OperatorSecurityRepo operatorSecurityRepo;
	@Autowired
OutputDestination consumer;

//	private <T> void normalPostTest (String uriStr, Object requestObj, T responseObj, Class<T> responseClass) {
//		testClient.post().uri(uriStr)
//		.contentType(MediaType.APPLICATION_JSON).bodyValue(requestObj).exchange().expectStatus().isOk()
//		.expectBody(responseClass).isEqualTo( responseObj);
//	}
	private void normalPutTest(String uriStr, Object requestObj) {
		testClient.put().uri(uriStr).bodyValue(requestObj).exchange().expectStatus().isOk();
	}

	@Test
	@Sql("fillTables.sql")
	void registerTest() throws IOException{
//		Operator operator = Operator.builder().operatorEmail(USER_NAME).operatorName(OPERATOR_NAME).build();
//		operatorSecurityRepo.save(operator);

		UserRegistrationRequestDto userRegistrationRequestDto = UserRegistrationRequestDto.builder().operatorName(OPERATOR_NAME)
				.userName(USER_NAME).password(PASSWORD).build();
		service.register(userRegistrationRequestDto).block();
		
		Operator operator = operatorSecurityRepo.findByOperatorName(OPERATOR_NAME);
		assertEquals(OPERATOR_NAME, operator.getOperatorName());
		assertEquals(USER_NAME, operator.getUserName());
		assertFalse(operator.getPassword().equals(""));
		log.debug("Saved password: {}", operator.getPassword());
		log.debug("password expiration: {}", operator.getPasswordExpiration());
		try {
			userRegistrationRequestDto = UserRegistrationRequestDto.builder().operatorName(OPERATOR_NAME+"1")
					.userName(USER_NAME).password(PASSWORD).build();
			service.register(userRegistrationRequestDto).block();
			log.debug("found by name {}", OPERATOR_NAME+"1");
		} catch (Exception e) {
		log.debug("Exception {}, operator () not found", e.getCause(),  OPERATOR_NAME+"1");
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

}

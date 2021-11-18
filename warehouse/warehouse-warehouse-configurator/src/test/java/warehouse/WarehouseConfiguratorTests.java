package warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

import static warehouse.dto.api.WarehouseConfiguratorApi.*;

import java.io.IOException;
import java.time.Instant;
import java.util.Date;

import javax.validation.constraints.NotEmpty;

import warehouse.service.interfaces.WarehouseConfiguratorService;
import warehouse.dto.JsonForKafkaDto;
import warehouse.dto.ParentDto;
import warehouse.dto.container.*;
import warehouse.dto.operator.*;
import warehouse.dto.product.*;
import warehouse.dto.role.*;
import warehouse.entities.*;
import warehouse.repo.*;

@SpringBootTest
@AutoConfigureWebTestClient
@AutoConfigureDataJpa
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(TestChannelBinderConfiguration.class)
public class WarehouseConfiguratorTests {
	private static final @NotEmpty String PRODUCT_NAME = "qwe";
	private static final @NotEmpty String ADDRESS = "asd";
	private static final @NotEmpty String OPERATOR = "poi";
	private static final @NotEmpty String ROLE = "lkj";
	@Value("${app-binding-name-docs:docs-out-0}")
	String dtoForDocBindingName;
	@Value("${app-binding-name-configurator:configurator-out-0}")
	String logsProviderArtifact;

	ObjectMapper mapper = new ObjectMapper();
	@Autowired
	WebTestClient testClient;
	@Autowired
	WarehouseConfiguratorService service;
	@Autowired
	ContainerWarehouseConfiguratorRepo containerRepo;
	@Autowired
	ProductWarehouseConfiguratorRepo productRepo;
	@Autowired
	OperatorWarehouseConfiguratorRepo operatorRepo;
	@Autowired
	RoleWarehouseConfiguratorRepo roleRepo;
	@Autowired
	OutputDestination consumer;	
	@Autowired
	InputDestination producer;

	private <T> void normalPostTest(String uriStr, Object requestObj, T responseObj, Class<T> responseClass) {
		testClient.post().uri(uriStr).contentType(MediaType.APPLICATION_JSON).bodyValue(requestObj).exchange()
				.expectStatus().isOk().expectBody(responseClass).isEqualTo(responseObj);
	}

	private <T> void wrongPostTest(String uriStr, Object requestObj, T responseObj, Class<T> responseClass) {
		testClient.post().uri(uriStr).contentType(MediaType.APPLICATION_JSON).bodyValue(requestObj).exchange()
				.expectStatus().is4xxClientError().expectBody(responseClass).isEqualTo(responseObj);
	}

	private void receiveFromClaudTest(ParentDto dtoExp)
			throws JsonMappingException, JsonProcessingException, ClassNotFoundException {
		Message<byte[]> message = consumer.receive(0, dtoForDocBindingName);
		String JsonForKafkaDtoJson = new String(message.getPayload());
		log.debug("receiveFromClaudTest: JsonForKafkaDtoJson {}", JsonForKafkaDtoJson);
		JsonForKafkaDto json = mapper.readValue(JsonForKafkaDtoJson, JsonForKafkaDto.class);
		String dtoForDocJson = json.getJsonDto();
		Class<?> dtoForDocJsonClass = Class.forName(json.getClassName());
		ParentDto dtoForDoc = (ParentDto) mapper.readValue(dtoForDocJson, dtoForDocJsonClass);
		log.debug("receiveFromClaudTest: dtoForDoc {}", dtoForDoc.toString());
		assertEquals(dtoExp, dtoForDoc);
	}
	
	private void sendToClaudTest(ParentDto dtoExp) {
		JsonForKafkaDto jsonForKafkaDto = new JsonForKafkaDto();
		try {
			String dtoForDocJson = mapper.writeValueAsString(dtoExp);
			jsonForKafkaDto.setClassName(dtoExp.getClass().getName());
			jsonForKafkaDto.setJsonDto(dtoForDocJson);
			log.debug("sendToClaudTest: dtoForDocJson {}", dtoForDocJson);
		} catch (JsonProcessingException e) {
			log.debug("Object was not serialized");
		}
		producer.send(new GenericMessage<JsonForKafkaDto>(jsonForKafkaDto));
	}

//	Container Product Tests
	@Test
	@Order(1)
	void createAndSaveContainerTest() throws JsonMappingException, JsonProcessingException, ClassNotFoundException {
		CreatingContainerDto newContainer = CreatingContainerDto.builder().address(ADDRESS).build();
		String responseMessageExp = String.format("the container with address %s created successfully", ADDRESS);
		normalPostTest(CONTAINER_CREATE, newContainer, responseMessageExp, String.class);

		Container container = containerRepo.findByAddress(ADDRESS);
		assertEquals(1, container.getContainerId());

		receiveFromClaudTest(newContainer);

		newContainer = CreatingContainerDto.builder().address(ADDRESS).build();
		responseMessageExp = String.format("Container with address %s already exist", ADDRESS);
		wrongPostTest(CONTAINER_CREATE, newContainer, responseMessageExp, String.class);
		
		
	}
	
	@Test
	@Order(14)
	void createAndSaveContainerAUTOTest() throws JsonMappingException, JsonProcessingException, ClassNotFoundException {
		CreatingContainerDto newContainerByAuto = CreatingContainerDto.builder().address(ADDRESS+"AUTO").build();
		sendToClaudTest(newContainerByAuto);
		Container containerAUTO = containerRepo.findByAddress(ADDRESS+"AUTO");
		log.debug("createAndSaveContainerTest: containerAUTO {}", containerAUTO);
	}

	@Test
	@Order(2)
	void createAndSaveProductTest() throws JsonMappingException, JsonProcessingException, ClassNotFoundException {
		CreatingProductDto newProduct = CreatingProductDto.builder().productName(PRODUCT_NAME).numberInContainer(1)
				.build();
		String responseMessageExp = String.format("the product %s created successfully", newProduct.getProductName());
		normalPostTest(PRODUCT_CREATE, newProduct, responseMessageExp, String.class);

		Product product = productRepo.findByProductName(PRODUCT_NAME);
		assertEquals(1, product.getProductId());

		receiveFromClaudTest(newProduct);

		newProduct = CreatingProductDto.builder().productName(PRODUCT_NAME).numberInContainer(1).build();
		responseMessageExp = String.format("Product %s already exist", PRODUCT_NAME);
		wrongPostTest(PRODUCT_CREATE, newProduct, responseMessageExp, String.class);
	}

	@Test
	@Order(3)
	void setProductToContainerTest() throws IOException, ClassNotFoundException {
		ProductToContainerSettingDto dto = ProductToContainerSettingDto.builder().containerId(1).productId(1)
				.settingDate(Date.from(Instant.now().plusSeconds(1000))).build();
		String responseMessageExp = String.format("the product %s has been successfully assigned to the container %s",
				1, 1);
		normalPostTest(SET_PRODUCT_TO_CONTAINER, dto, responseMessageExp, String.class);
		assertEquals(1, containerRepo.findByAddress(ADDRESS).getProduct().getProductId());

		receiveFromClaudTest(dto);

		dto = ProductToContainerSettingDto.builder().containerId(1).productId(2)
				.settingDate(Date.from(Instant.now().plusSeconds(1000))).build();
		responseMessageExp = "No product with id 2";
		wrongPostTest(SET_PRODUCT_TO_CONTAINER, dto, responseMessageExp, String.class);

		dto = ProductToContainerSettingDto.builder().containerId(2).productId(1)
				.settingDate(Date.from(Instant.now().plusSeconds(1000))).build();
		responseMessageExp = "container with id 2 not found";
		wrongPostTest(SET_PRODUCT_TO_CONTAINER, dto, responseMessageExp, String.class);
	}

	@Test
	@Order(7)
	void changeContainerAddressTest() throws IOException, ClassNotFoundException {
		ChangeContainerAddressDto dto = ChangeContainerAddressDto.builder().containerId(1).newAddress(ADDRESS + 1)
				.build();
		String responseMessageExp = String.format("the address of the container with id %s changed to %s", 1,
				ADDRESS + 1);
		normalPostTest(CHANGE_CONTAINER_ADDRESS, dto, responseMessageExp, String.class);
		assertEquals(ADDRESS + 1, containerRepo.findByAddress(ADDRESS + 1).getAddress());

		receiveFromClaudTest(dto);

		dto = ChangeContainerAddressDto.builder().containerId(2).newAddress(ADDRESS + 1).build();
		responseMessageExp = "container with id 2 not found";
		wrongPostTest(CHANGE_CONTAINER_ADDRESS, dto, responseMessageExp, String.class);
	}

	@Test
	@Order(8)
	void changeProductNameTest() throws IOException, ClassNotFoundException {
		ChangeProductNameDto dto = ChangeProductNameDto.builder().productId(1).newProductName(PRODUCT_NAME + 1).build();
		String responseMessageExp = "the name of the product with id 1 was changed to " + PRODUCT_NAME + 1;
		normalPostTest(CHANGE_PRODUCT_NAME, dto, responseMessageExp, String.class);
		assertEquals(PRODUCT_NAME + 1, productRepo.findByProductName(PRODUCT_NAME + 1).getProductName());

		receiveFromClaudTest(dto);

		dto = ChangeProductNameDto.builder().productId(2).newProductName(ADDRESS + 1).build();
		responseMessageExp = "product with id 2 not found";
		wrongPostTest(CHANGE_PRODUCT_NAME, dto, responseMessageExp, String.class);
	}

	@Test
	@Order(9)
	void setTransportSupplyTest() throws IOException, ClassNotFoundException {
		TransportSupplySettingDto dto = TransportSupplySettingDto.builder().productId(1).transportSupply(1)
				.settingDate(Date.from(Instant.now().plusSeconds(1000))).build();
		String responseMessageExp = "the transport supply in 1 days was settled to product with id 1";
		normalPostTest(SET_TRANSPORT_SUPPLY_TO_PRODUCT, dto, responseMessageExp, String.class);
		assertEquals(1, productRepo.findByProductName(PRODUCT_NAME + 1).getTransportSupply());

		receiveFromClaudTest(dto);

		dto = TransportSupplySettingDto.builder().productId(2).transportSupply(1)
				.settingDate(Date.from(Instant.now().plusSeconds(1000))).build();
		responseMessageExp = "product with id 2 not found";
		wrongPostTest(SET_TRANSPORT_SUPPLY_TO_PRODUCT, dto, responseMessageExp, String.class);
	}

	@Test
	@Order(10)
	void setIrreducibleBalanceTest() throws IOException, ClassNotFoundException {
		IrreducibleBalanceSettingDto dto = IrreducibleBalanceSettingDto.builder().productId(1).irreducibleBalance(1)
				.settingDate(Date.from(Instant.now().plusSeconds(1000))).build();
		String responseMessageExp = "the irreducible balance in 1 items was settled to product with id 1";
		normalPostTest(SET_IRREDUCIBLE_BALANCE_TO_PRODUCT, dto, responseMessageExp, String.class);
		assertEquals(1, productRepo.findByProductName(PRODUCT_NAME + 1).getIrreducibleBalance());

		receiveFromClaudTest(dto);

		dto = IrreducibleBalanceSettingDto.builder().productId(2).irreducibleBalance(1)
				.settingDate(Date.from(Instant.now().plusSeconds(1000))).build();
		responseMessageExp = "product with id 2 not found";
		wrongPostTest(SET_IRREDUCIBLE_BALANCE_TO_PRODUCT, dto, responseMessageExp, String.class);
	}

//	Operator Role Tests
	@Test
	@Order(4)
	void createAndSaveOperatorTest() throws JsonMappingException, JsonProcessingException, ClassNotFoundException {
		CreatingOperatorDto newOperator = CreatingOperatorDto.builder().operatorName(OPERATOR)
				.operatorEmail(OPERATOR + "@gmail.com").build();
		String responseMessageExp = String.format("the operator %s created successfully", OPERATOR);
		normalPostTest(OPERATOR_CREATE, newOperator, responseMessageExp, String.class);

		assertEquals(1, operatorRepo.findByOperatorName(OPERATOR).getOperatorId());

		receiveFromClaudTest(newOperator);

		responseMessageExp = String.format("Operator %s already exist", OPERATOR);
		wrongPostTest(OPERATOR_CREATE, newOperator, responseMessageExp, String.class);
	}

	@Test
	@Order(5)
	void createAndSaveRoleTest() throws JsonMappingException, JsonProcessingException, ClassNotFoundException {
		CreatingRoleDto newRole = CreatingRoleDto.builder().role(ROLE).build();
		String responseMessageExp = String.format("the role %s created successfully", ROLE);
		normalPostTest(ROLE_CREATE, newRole, responseMessageExp, String.class);

		assertEquals(1, roleRepo.findByRole(ROLE).getRoleId());

		receiveFromClaudTest(newRole);
		responseMessageExp = String.format("Role %s already exist", ROLE);

	}

	@Test
	@Order(6)
	void setOperatorToRoleTest() throws JsonMappingException, JsonProcessingException, ClassNotFoundException {
		OperatorToRoleSettingDto dto = OperatorToRoleSettingDto.builder().operatorId(1).roleId(1)
				.settingDate(Date.from(Instant.now().plusSeconds(1000))).build();
		setExecutorOperatorId(1, dto);
		log.debug("setOperatorToRoleTest: operatorToRoleSettingDto {}", dto);
		String responseMessageExp = "the 1 role has been successfully assigned to the operator 1";
		normalPostTest(SET_OPERATOR_TO_ROLE, dto, responseMessageExp, String.class);
		assertEquals(1, roleRepo.findByRole(ROLE).getOperator().getOperatorId());

		receiveFromClaudTest(dto);

		dto = OperatorToRoleSettingDto.builder().operatorId(1).roleId(2)
				.settingDate(Date.from(Instant.now().plusSeconds(1000))).build();
		responseMessageExp = "role with id 2 not found";
		wrongPostTest(SET_OPERATOR_TO_ROLE, dto, responseMessageExp, String.class);

		dto = OperatorToRoleSettingDto.builder().operatorId(2).roleId(1)
				.settingDate(Date.from(Instant.now().plusSeconds(1000))).build();
		responseMessageExp = "No operator with id 2";
		wrongPostTest(SET_OPERATOR_TO_ROLE, dto, responseMessageExp, String.class);
	}

	private void setExecutorOperatorId(long executorOperatorId, ParentDto dtoParent) {
		dtoParent.setExecutorOperatorId(executorOperatorId);
	}

	@Test
	@Order(11)
	void changeOperatorNameTest() throws JsonMappingException, JsonProcessingException, ClassNotFoundException {
		ChangeOperatorNameDto dto = ChangeOperatorNameDto.builder().operatorId(1).newOperatorName(OPERATOR + 1).build();
		String responseMessageExp = "the name of the operator with id 1 was changed to " + OPERATOR + 1;
		normalPostTest(CHANGE_OPERATOR_NAME, dto, responseMessageExp, String.class);
		assertEquals(OPERATOR + 1, operatorRepo.findByOperatorName(OPERATOR + 1).getOperatorName());

		receiveFromClaudTest(dto);

		dto = ChangeOperatorNameDto.builder().operatorId(2).newOperatorName(OPERATOR + 1).build();
		responseMessageExp = "operator with id 2 not found";
		wrongPostTest(CHANGE_OPERATOR_NAME, dto, responseMessageExp, String.class);
	}

	@Test
	@Order(12)
	void changeOperatorEmailTest() throws JsonMappingException, JsonProcessingException, ClassNotFoundException {
		ChangeOperatorEmailDto dto = ChangeOperatorEmailDto.builder().operatorId(1)
				.newOperatorEmail(OPERATOR + 1 + "@gmail.com").build();
		String responseMessageExp = "the email of the operator with id 1 was changed to " + OPERATOR + 1 + "@gmail.com";
		normalPostTest(CHANGE_OPERATOR_EMAIL, dto, responseMessageExp, String.class);
		assertEquals(OPERATOR + 1, operatorRepo.findByOperatorName(OPERATOR + 1).getOperatorName());

		receiveFromClaudTest(dto);

		dto = ChangeOperatorEmailDto.builder().operatorId(2).newOperatorEmail(OPERATOR + 1 + "@gmail.com").build();
		responseMessageExp = "operator with id 2 not found";
		wrongPostTest(CHANGE_OPERATOR_EMAIL, dto, responseMessageExp, String.class);
	}

	@Test
	@Order(13)
	void changeRoleTest() throws JsonMappingException, JsonProcessingException, ClassNotFoundException {
		ChangeRoleDto dto = ChangeRoleDto.builder().roleId(1).newRole(ROLE + 1).build();
		String responseMessageExp = "the role with id 1 was changed to " + ROLE + 1;
		normalPostTest(CHANGE_ROLE, dto, responseMessageExp, String.class);
		assertEquals(ROLE + 1, roleRepo.findByRole(ROLE + 1).getRole());

		receiveFromClaudTest(dto);

		dto = ChangeRoleDto.builder().roleId(2).newRole(ROLE + 1).build();
		responseMessageExp = "role with id 2 not found";
		wrongPostTest(CHANGE_ROLE, dto, responseMessageExp, String.class);
	}

}

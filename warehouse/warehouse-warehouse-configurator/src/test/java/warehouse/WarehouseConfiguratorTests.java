package warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
import warehouse.dto.DocumentDtosParent;
import warehouse.dto.container.*;
import warehouse.dto.product.*;
import warehouse.entities.*;
import warehouse.repo.ContainerWarehouseConfiguratorRepo;
import warehouse.repo.ProductWarehouseConfiguratorRepo;

@SpringBootTest
@AutoConfigureWebTestClient
@AutoConfigureDataJpa
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(TestChannelBinderConfiguration.class)
public class WarehouseConfiguratorTests {
private static final @NotEmpty String PRODUCT_NAME = "qwe";
private static final @NotEmpty String ADDRESS = "asd";
@Value("${app-binding-name-docs:docs-out-0}")
String dtoForDocBindingName;

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
OutputDestination consumer;

//	private <T> void normalPostTest (String uriStr, Object requestObj, T responseObj, Class<T> responseClass) {
//		testClient.post().uri(uriStr)
//		.contentType(MediaType.APPLICATION_JSON).bodyValue(requestObj).exchange().expectStatus().isOk()
//		.expectBody(responseClass).isEqualTo( responseObj);
//	}
	private void normalPutTest(String uriStr, Object requestObj) {
		testClient.put().uri(uriStr).bodyValue(requestObj).exchange().expectStatus().isOk();
	}

//	ContainerTests
	@Test
	@Order(1)
	void normalCreateAndSaveContainerTest() {
		CreatingContainerDto newContainer = new CreatingContainerDto(ADDRESS);
		normalPutTest(CONTAINER_CREATE, newContainer);
		newContainer = new CreatingContainerDto(ADDRESS);
		try {
			service.createAndSaveContainer(newContainer);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
		newContainer = new CreatingContainerDto(null);
		try {
			service.createAndSaveContainer(newContainer);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}
	}

	@Test
	@Order(3)
	void setProductToContainerTest() throws IOException{
		service.setProductToContainer(new ProductToContainerSettingDto(new Date(10), 1, 1));
		Product productExp = productRepo.findByProductName(PRODUCT_NAME);
		assertEquals(productExp, containerRepo.findByAddress(ADDRESS).getProduct());
		
		Message<byte[]> message = consumer.receive(0, dtoForDocBindingName);
		String dtoForDocJson = new String(message.getPayload());
		System.out.println("++++++++++++++++++++++++"+dtoForDocJson);
		ProductToContainerSettingDtoForDoc dtoForDoc  = (ProductToContainerSettingDtoForDoc) mapper.readValue(dtoForDocJson, DocumentDtosParent.class);
		ProductToContainerSettingDtoForDoc dtoForDocExp = ProductToContainerSettingDtoForDoc.builder()
				.productToContainerSettingDate(null)
				.containerId(1)
				.productId(1)
				.creatingMethod(null)
				.operatorId(0).build();
		assertEquals(dtoForDoc, dtoForDocExp);
	}
//	ProducTests
	@Test
	@Order(2)
	void normalCreateAndSaveProductTest() {
		CreatingProductDto newProduct = new CreatingProductDto(PRODUCT_NAME, 1);
		service.createAndSaveProduct(newProduct);
		Product Product = productRepo.findByProductName(PRODUCT_NAME);
		assertEquals(1, Product.getProductId());

		newProduct = new CreatingProductDto(PRODUCT_NAME, 2);
		try {
			service.createAndSaveProduct(newProduct);
		} catch (Exception e) {
			log.debug(e.getMessage());
		}

	}
}

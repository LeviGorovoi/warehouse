package warehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import lombok.extern.slf4j.Slf4j;
import warehouse.dto.container.CreatingContainerDto;
import warehouse.entities.Container;
import warehouse.service.impl.WarehouseStateBackOficeServiceImpl;

import static warehouse.dto.api.WarehouseConfiguratorApi.*;

import javax.validation.constraints.NotEmpty;

import org.junit.jupiter.api.*;
@Slf4j
@SpringBootTest
@AutoConfigureWebTestClient
public class WarehouseStateBackOficeTests {
	private static final @NotEmpty String ADDRESS = "asd";
@Autowired
WebTestClient webTestClient;

private void normalPutTest(String uriStr, Object requestObj) {
	Container res = webTestClient.put().uri(uriStr).bodyValue(requestObj).exchange().expectStatus().isOk()
			.returnResult(Container.class).getResponseBody().blockFirst();
//	log.debug("saved and recieved container {}", res.toString());
}

@Test
void normalCreateAndSaveContainerTest() {
	log.debug("creating test-container wiht address {}", ADDRESS);
	CreatingContainerDto newContainer = new CreatingContainerDto(ADDRESS);
	normalPutTest(CONTAINER_CREATE, newContainer);
}
}

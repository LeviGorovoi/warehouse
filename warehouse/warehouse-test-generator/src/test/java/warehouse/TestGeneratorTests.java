package warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.OutputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.Message;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import warehouse.dto.JsonForKafkaDto;
import warehouse.dto.ParentDto;
import warehouse.repo.ContainerTestGeneratorRepo;
import warehouse.service.interfaces.TestGeneratorService;


@SpringBootTest
@AutoConfigureWebTestClient
@AutoConfigureDataJpa
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Import(TestChannelBinderConfiguration.class)
public class TestGeneratorTests {
	
	@Autowired
	TestGeneratorService service;
	@Autowired
	ContainerTestGeneratorRepo containerRepo;
	@Autowired
	OutputDestination consumer;
	ObjectMapper mapper = new ObjectMapper();
	
	@Value("${app-binding-name-configurator:sendDtoToConfigurator-out-0}")
	String dtoForConfiguratorBindingName;
	
	private ParentDto receiveFromClaud()
			throws JsonMappingException, JsonProcessingException, ClassNotFoundException {
		Message<byte[]> message = consumer.receive(1, dtoForConfiguratorBindingName);
		String JsonForKafkaDtoJson = new String(message.getPayload());
//		log.debug("receiveFromClaudTest: JsonForKafkaDtoJson {}", JsonForKafkaDtoJson);
		JsonForKafkaDto json = mapper.readValue(JsonForKafkaDtoJson, JsonForKafkaDto.class);
		String dtoForDocJson = json.getJsonDto();
		Class<?> dtoForDocJsonClass = Class.forName(json.getClassName());
		ParentDto dtoForConfigurator = (ParentDto) mapper.readValue(dtoForDocJson, dtoForDocJsonClass);
		log.debug("receiveFromClaudTest: dtoForDoc {}", dtoForConfigurator.toString());
		return dtoForConfigurator;
	}
	@Test
	@Order(1)
	void createAndSaveContainerTest() throws JsonMappingException, JsonProcessingException, ClassNotFoundException, InterruptedException {
		
		receiveFromClaud();
		Thread.sleep(1000);
		receiveFromClaud();
	}
}

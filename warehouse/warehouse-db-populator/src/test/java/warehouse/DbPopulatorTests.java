package warehouse;

import javax.validation.constraints.NotEmpty;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.stream.binder.test.InputDestination;
import org.springframework.cloud.stream.binder.test.TestChannelBinderConfiguration;
import org.springframework.context.annotation.Import;
import org.springframework.messaging.support.GenericMessage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import warehouse.dto.JsonForKafkaDto;
import warehouse.dto.role.CreatingRoleDto;
import warehouse.repo.DocsDbPopulatorRepo;

@SpringBootTest
@Import(TestChannelBinderConfiguration.class)
@Slf4j
public class DbPopulatorTests {
	private static final @NotEmpty String ROLE = "lkj";
	@Autowired
	InputDestination input;
	@Autowired
	DocsDbPopulatorRepo repo;
	@Autowired
	ObjectMapper objectMapper;
	
	@Test
	void DbPopulatorTest() {
		CreatingRoleDto creatingRoleDto = CreatingRoleDto.builder().role(ROLE).build();
		JsonForKafkaDto jsonForKafkaDto = new JsonForKafkaDto();
		try {
			String dtoForDocJson = objectMapper.writeValueAsString(creatingRoleDto);
			jsonForKafkaDto.setClassName(creatingRoleDto.getClass().getName());
			jsonForKafkaDto.setJsonDto(dtoForDocJson);
			log.debug("DbPopulatorTest: dtoForDocJson {}", dtoForDocJson);
		} catch (JsonProcessingException e) {
			log.debug("Object was not serialized");
		}
		input.send(new GenericMessage<JsonForKafkaDto>(jsonForKafkaDto));
		
		
	}
}

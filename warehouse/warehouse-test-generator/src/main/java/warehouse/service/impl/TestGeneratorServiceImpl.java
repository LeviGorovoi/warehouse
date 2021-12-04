package warehouse.service.impl;


import java.time.Instant;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import warehouse.dto.JsonForKafkaDto;
import warehouse.dto.container.CreatingContainerDto;
import warehouse.repo.ContainerTestGeneratorRepo;
import warehouse.service.interfaces.TestGeneratorService;

@Service
@Slf4j
public class TestGeneratorServiceImpl implements TestGeneratorService {
	@Autowired
	StreamBridge streamBridge;
	@Autowired
	ObjectMapper objectMapper;
	@Autowired
	ContainerTestGeneratorRepo containerRepo;
	
int counter;

	@Override
	public JsonForKafkaDto generateTestDto() {
CreatingContainerDto dto = CreatingContainerDto.builder().address("AUTO"+counter).build();
//log.debug("counter : {}", counter);
counter++;

return createJsonForKafkaDto(dto);
	}
	
	private  <T> JsonForKafkaDto createJsonForKafkaDto(T docDto) {
		JsonForKafkaDto jsonForKafkaDto = new JsonForKafkaDto();
		try {
			String dtoForDocJson = objectMapper.writeValueAsString(docDto);
			jsonForKafkaDto.setClassName(docDto.getClass().getName());
			jsonForKafkaDto.setJsonDto(dtoForDocJson);
//			log.debug("createJsonForKafkaDto: dtoForDocJson {}", dtoForDocJson);
		} catch (JsonProcessingException e) {
			log.debug("Object was not serialized");
		}
		return jsonForKafkaDto;
	}
	@PreDestroy
	public void onDestroy() throws InterruptedException {
		log.debug("last counter : {}", counter);
		long start = System.currentTimeMillis();
		long repoSize = 0;;
		while(repoSize!=counter) {
			Thread.sleep(5);
			repoSize = containerRepo.count();
		}
		long end = System.currentTimeMillis();
		log.debug("processing time : {}", end-start);
		
	}
	
}

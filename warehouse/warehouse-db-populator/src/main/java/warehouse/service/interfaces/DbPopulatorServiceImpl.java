package warehouse.service.interfaces;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import warehouse.doc.WarehoseDoc;
import warehouse.dto.JsonForKafkaDto;
import warehouse.dto.ParentDto;
import warehouse.repo.DocsDbPopulatorRepo;

@Service
@Slf4j
public class DbPopulatorServiceImpl implements DbPopulatorService {
	ObjectMapper mapper = new ObjectMapper();
	@Autowired
	DocsDbPopulatorRepo repo;

	WarehoseDoc getDocFromJsonForKafkaDto(JsonForKafkaDto jsonForKafkaDto)
			throws ClassNotFoundException, JsonMappingException, JsonProcessingException {
//		log.debug("getDocFromJsonForKafkaDto: jsonForKafkaDto {} received", jsonForKafkaDto);
		String jsonDto = jsonForKafkaDto.getJsonDto();
		Class<?> jsonDtoClass = Class.forName(jsonForKafkaDto.getClassName());
		ParentDto dtoForDoc =  (ParentDto) mapper.readValue(jsonDto, jsonDtoClass);
		WarehoseDoc doc = WarehoseDoc.builder().documentDateTime(LocalDateTime.now()).incomingDto(dtoForDoc)
				.incomingDtoType(jsonDtoClass.getSimpleName()).build();
//		log.debug("getDocFromJsonForKafkaDto: doc {}", doc.toString());
		return doc;
	}

	@Override
	public void saveDocInDb(JsonForKafkaDto jsonDto) throws JsonMappingException, ClassNotFoundException, JsonProcessingException {
		WarehoseDoc doc = getDocFromJsonForKafkaDto(jsonDto);
		repo.save(doc).subscribe(d->{
//			log.debug("saveDocInDb: doc {}", d.toString());		
		});
	}

}

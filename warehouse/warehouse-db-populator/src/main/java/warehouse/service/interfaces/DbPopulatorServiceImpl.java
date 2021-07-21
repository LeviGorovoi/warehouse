package warehouse.service.interfaces;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import warehouse.doc.WarehoseDoc;
import warehouse.dto.JsonForJournalingDto;
import warehouse.dto.ParentDto;
import warehouse.repo.DocsDbPopulatorRepo;

@Service
@Slf4j
public class DbPopulatorServiceImpl implements DbPopulatorService {
	ObjectMapper mapper = new ObjectMapper();
	@Autowired
	DocsDbPopulatorRepo repo;

	WarehoseDoc getDocFromJsonForJournalingDto(JsonForJournalingDto jsonDto)
			throws ClassNotFoundException, JsonMappingException, JsonProcessingException {
		log.debug("getDocFromJsonForJournalingDto: JsonForJournalingDtoJson {} received", jsonDto);
		String dtoForDocJson = jsonDto.getJsonForJournaling();
		Class<?> dtoForDocJsonClass = Class.forName(jsonDto.getClassName());
		ParentDto dtoForDoc = (ParentDto) mapper.readValue(dtoForDocJson, dtoForDocJsonClass);
		WarehoseDoc doc = WarehoseDoc.builder().documentDateTime(LocalDateTime.now()).incomingDto(dtoForDoc)
				.incomingDtoType(dtoForDocJsonClass.getSimpleName()).build();
		log.debug("getDocFromJsonForJournalingDto: doc {}", doc.toString());
		return doc;
	}

	@Override
	public Mono<WarehoseDoc> saveDocInDb(JsonForJournalingDto jsonDto) throws JsonMappingException, ClassNotFoundException, JsonProcessingException {
		WarehoseDoc doc = getDocFromJsonForJournalingDto(jsonDto);
		return repo.save(doc).doOnSuccess(d->{
			log.debug("saveDocInDb: doc {}", d.toString());
		});
	}

}

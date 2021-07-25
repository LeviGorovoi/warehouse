package warehouse.service.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import reactor.core.publisher.Mono;
import warehouse.doc.WarehoseDoc;
import warehouse.dto.JsonForJournalingDto;

public interface DbPopulatorService {
void saveDocInDb(JsonForJournalingDto jsonDto) throws JsonMappingException, ClassNotFoundException, JsonProcessingException;
}

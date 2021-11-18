package warehouse.service.interfaces;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

import reactor.core.publisher.Mono;
import warehouse.doc.WarehoseDoc;
import warehouse.dto.JsonForKafkaDto;

public interface DbPopulatorService {
void saveDocInDb(JsonForKafkaDto jsonDto) throws JsonMappingException, ClassNotFoundException, JsonProcessingException;
}

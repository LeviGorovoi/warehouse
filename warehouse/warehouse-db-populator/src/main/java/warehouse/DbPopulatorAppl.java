package warehouse;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;
import warehouse.dto.JsonForKafkaDto;
import warehouse.service.interfaces.DbPopulatorService;

@SpringBootApplication
@Slf4j
public class DbPopulatorAppl {
	@Autowired
	DbPopulatorService service;

	public static void main(String[] args) {
		SpringApplication.run(DbPopulatorAppl.class, args);
	}

	@Bean
	Consumer<JsonForKafkaDto> getJsonForJournalingDtoConsumer() {
		return t -> {
			try {
				service.saveDocInDb(t);
			} catch (ClassNotFoundException | JsonProcessingException e) {
				log.debug("{}", e);
			}
		};
	}

}

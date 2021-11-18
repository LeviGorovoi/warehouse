package warehouse;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.core.JsonProcessingException;

import lombok.extern.slf4j.Slf4j;
import warehouse.controllers.KafkaReceiver;
import warehouse.dto.JsonForKafkaDto;

@SpringBootApplication
@Slf4j
public class WarehouseConfiguratorAppl {
	@Autowired
	KafkaReceiver service;

	public static void main(String[] args) {
		SpringApplication.run(WarehouseConfiguratorAppl.class, args);
	}

	@Bean
	Consumer<JsonForKafkaDto> getJsonFromFafka() {
		return t -> {
			try {
				service.receiveDto(t);
			} catch (JsonProcessingException | ClassNotFoundException e) {
				log.debug("{}", e);
			}
		};
	}
	
}

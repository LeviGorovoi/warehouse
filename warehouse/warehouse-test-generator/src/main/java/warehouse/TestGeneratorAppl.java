package warehouse;


import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import lombok.extern.slf4j.Slf4j;
import warehouse.dto.JsonForKafkaDto;
import warehouse.service.impl.TestGeneratorServiceImpl;
import warehouse.service.interfaces.TestGeneratorService;
@Slf4j
@SpringBootApplication
public class TestGeneratorAppl {
@Autowired
TestGeneratorService service;

	private static final long TIME_OUT = 10000;
	public static void main(String[] args) throws InterruptedException {
		ConfigurableApplicationContext ctx = SpringApplication.run(TestGeneratorAppl.class, args);
		Thread.sleep(TIME_OUT);
		
		
		
		ctx.close();
	}

	@Bean
Supplier<JsonForKafkaDto>  sendDtoToConfigurator() {
	return this::generateTestDto;
}
	JsonForKafkaDto generateTestDto() {
		JsonForKafkaDto dto = service.generateTestDto();		
		return dto;
	}
}

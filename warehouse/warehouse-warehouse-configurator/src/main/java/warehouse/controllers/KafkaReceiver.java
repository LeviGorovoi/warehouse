package warehouse.controllers;

import static warehouse.dto.api.WarehouseConfiguratorApi.CHANGE_CONTAINER_ADDRESS;
import static warehouse.dto.api.WarehouseConfiguratorApi.CHANGE_OPERATOR_EMAIL;
import static warehouse.dto.api.WarehouseConfiguratorApi.CHANGE_OPERATOR_NAME;
import static warehouse.dto.api.WarehouseConfiguratorApi.CHANGE_PRODUCT_NAME;
import static warehouse.dto.api.WarehouseConfiguratorApi.CHANGE_ROLE;
import static warehouse.dto.api.WarehouseConfiguratorApi.CONTAINER_CREATE;
import static warehouse.dto.api.WarehouseConfiguratorApi.OPERATOR_CREATE;
import static warehouse.dto.api.WarehouseConfiguratorApi.PRODUCT_CREATE;
import static warehouse.dto.api.WarehouseConfiguratorApi.ROLE_CREATE;
import static warehouse.dto.api.WarehouseConfiguratorApi.SET_IRREDUCIBLE_BALANCE_TO_PRODUCT;
import static warehouse.dto.api.WarehouseConfiguratorApi.SET_OPERATOR_TO_ROLE;
import static warehouse.dto.api.WarehouseConfiguratorApi.SET_PRODUCT_TO_CONTAINER;
import static warehouse.dto.api.WarehouseConfiguratorApi.SET_TRANSPORT_SUPPLY_TO_PRODUCT;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Consumer;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import warehouse.dto.JsonForKafkaDto;
import warehouse.dto.ParentDto;
import warehouse.dto.container.ChangeContainerAddressDto;
import warehouse.dto.container.CreatingContainerDto;
import warehouse.dto.container.ProductToContainerSettingDto;
import warehouse.dto.operator.ChangeOperatorEmailDto;
import warehouse.dto.operator.ChangeOperatorNameDto;
import warehouse.dto.operator.CreatingOperatorDto;
import warehouse.dto.product.ChangeProductNameDto;
import warehouse.dto.product.CreatingProductDto;
import warehouse.dto.product.IrreducibleBalanceSettingDto;
import warehouse.dto.product.TransportSupplySettingDto;
import warehouse.dto.role.ChangeRoleDto;
import warehouse.dto.role.CreatingRoleDto;
import warehouse.dto.role.OperatorToRoleSettingDto;
import warehouse.service.interfaces.WarehouseConfiguratorService;

@Service
@Slf4j
public class KafkaReceiver {	
	@Autowired
	WarehouseConfiguratorService service;
	
	ObjectMapper mapper = new ObjectMapper();

	
	HashMap<String, Consumer<ParentDto>> map = new HashMap<>();
	{
		map.put("CreatingContainerDto", (dto)->createAndSaveContainer((CreatingContainerDto) dto));
	}
	
	ParentDto getDocFromJsonForKafkaDto(JsonForKafkaDto jsonForKafkaDto)
			throws ClassNotFoundException, JsonMappingException, JsonProcessingException {
		log.debug("getDocFromJsonForKafkaDto: getDocFromJsonForKafkaDto {} received", jsonForKafkaDto);
		String jsonDto = jsonForKafkaDto.getJsonDto();
		Class<?> jsonDtoClass = Class.forName(jsonForKafkaDto.getClassName());
		ParentDto dto = (ParentDto) mapper.readValue(jsonDto, jsonDtoClass);
		return dto;
	}
	
	public void receiveDto(JsonForKafkaDto jsonForKafkaDto) throws JsonMappingException, JsonProcessingException, ClassNotFoundException {
		ParentDto dto = getDocFromJsonForKafkaDto(jsonForKafkaDto);
		String dtoClassName = dto.getClass().getSimpleName();
		map.get(dtoClassName).accept(dto);
		
	}

	
	void createAndSaveContainer(CreatingContainerDto dto) {
		service.createAndSaveContainer(dto).block();
	}

//	@PostMapping(PRODUCT_CREATE)
//	void createAndSaveContainer(@Valid @RequestBody CreatingProductDto dto) {
//		return service.createAndSaveProduct(dto);
//	}
//
//	@PostMapping(OPERATOR_CREATE)
//	Mono<String> createAndSaveContainer(@Valid @RequestBody CreatingOperatorDto dto) {
//		return service.createAndSaveOperator(dto);
//	}
//
//	@PostMapping(ROLE_CREATE)
//	Mono<String> createAndSaveRole(@Valid @RequestBody CreatingRoleDto dto) {
//		return service.createAndSaveRole(dto);
//	}
//
//	@PostMapping(SET_OPERATOR_TO_ROLE)
//	Mono<String> setOperatorToRole(@Valid @RequestBody OperatorToRoleSettingDto dto) {
//		return service.setOperatorToRole(dto);
//	}
//
//	@PostMapping(SET_PRODUCT_TO_CONTAINER)
//	Mono<String> setProductToContainer(@Valid @RequestBody ProductToContainerSettingDto dto) {
//		return service.setProductToContainer(dto);
//	}
//
//	@PostMapping(CHANGE_CONTAINER_ADDRESS)
//	Mono<String> changeContainerAddress(@Valid @RequestBody ChangeContainerAddressDto dto) {
//		return service.changeContainerAddress(dto);
//	}
//
//	@PostMapping(CHANGE_PRODUCT_NAME)
//	Mono<String> changeProductName(@Valid @RequestBody ChangeProductNameDto dto) {
//		return service.changeProductName(dto);
//	}
//
//	@PostMapping(SET_TRANSPORT_SUPPLY_TO_PRODUCT)
//	Mono<String> setTransportSupply(@Valid @RequestBody TransportSupplySettingDto dto) {
//		return service.setTransportSupply(dto);
//	}
//
//	@PostMapping(SET_IRREDUCIBLE_BALANCE_TO_PRODUCT)
//	Mono<String> setIrreducibleBalance(@Valid @RequestBody IrreducibleBalanceSettingDto dto) {
//		return service.setIrreducibleBalance(dto);
//	}
//
//	@PostMapping(CHANGE_OPERATOR_NAME)
//	Mono<String> changeOperatorName(@Valid @RequestBody ChangeOperatorNameDto dto) {
//		return service.changeOperatorName(dto);
//	}
//	
//	@PostMapping(CHANGE_OPERATOR_EMAIL)
//	Mono<String> changeOperatorEmail(@Valid @RequestBody ChangeOperatorEmailDto dto) {
//		return service.changeOperatorEmail(dto);
//	}
//	
//	@PostMapping(CHANGE_ROLE)
//	Mono<String> changeRole(@Valid @RequestBody ChangeRoleDto dto) {
//		return service.changeRole(dto);
//	}
}

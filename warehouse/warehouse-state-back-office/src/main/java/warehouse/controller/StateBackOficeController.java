package warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import warehouse.service.interfaces.StateBackOficeService;
import warehouse.dto.container.*;
import warehouse.dto.operator.ChangeOperatorEmailDto;
import warehouse.dto.operator.ChangeOperatorNameDto;
import warehouse.dto.operator.CreatingOperatorDto;
import warehouse.dto.product.*;
import warehouse.dto.role.ChangeRoleDto;
import warehouse.dto.role.CreatingRoleDto;
import warehouse.dto.role.OperatorToRoleSettingDto;

import static warehouse.dto.api.WarehouseConfiguratorApi.*;

import javax.validation.Valid;

@RestController
@Slf4j
public class StateBackOficeController {
	@Autowired
	StateBackOficeService service;

	

	@PostMapping(CONTAINER_CREATE)
	Mono<String> createAndSaveContainer(@Valid @RequestBody CreatingContainerDto dto, @RequestParam String username) {
		return service.createAndSaveContainer(dto, username);
	}
	
	@PostMapping(CHANGE_CONTAINER_ADDRESS)
	public Mono<String> changeContainerAddress(@Valid @RequestBody ChangeContainerAddressDto dto, @RequestParam String username) {
		return service.changeContainerAddress(dto, username);
	}

	@PostMapping(SET_PRODUCT_TO_CONTAINER)
	public Mono<String> setProductToContainer(@Valid @RequestBody ProductToContainerSettingDto dto, @RequestParam String username) {
		return service.setProductToContainer(dto, username);
	}

	@PostMapping(PRODUCT_CREATE)
	Mono<String> createAndSaveProduct(@Valid @RequestBody CreatingProductDto dto, @RequestParam String username) {
		return service.createAndSaveProduct(dto, username);
	}
	
	@PostMapping(CHANGE_PRODUCT_NAME)
	Mono<String> changeProdactName(@Valid @RequestBody ChangeProductNameDto dto, @RequestParam String username) {
		return service.changeProdactName(dto, username);
	}
	
	@PostMapping(SET_TRANSPORT_SUPPLY_TO_PRODUCT)
	Mono<String> setTransportSupply(@Valid @RequestBody TransportSupplySettingDto dto, @RequestParam String username) {
		return service.setTransportSupply(dto, username);
	}

	@PostMapping(SET_IRREDUCIBLE_BALANCE_TO_PRODUCT)
	Mono<String> setIrreducibleBalance(@Valid @RequestBody IrreducibleBalanceSettingDto dto, @RequestParam String username) {
		return service.setIrreducibleBalance(dto, username);
	}

	@PostMapping(OPERATOR_CREATE)
	Mono<String> createAndSaveOperator(@Valid @RequestBody CreatingOperatorDto dto, @RequestParam String username) {
		return service.createAndSaveOperator(dto, username);
	}
	
	@PostMapping(CHANGE_OPERATOR_NAME)
	Mono<String> changeOperatorName(@Valid @RequestBody ChangeOperatorNameDto dto, @RequestParam String username) {
		return service.changeOperatorName(dto, username);
	}
	
	@PostMapping(CHANGE_OPERATOR_EMAIL)
	Mono<String> changeOperatorEmail(@Valid @RequestBody ChangeOperatorEmailDto dto, @RequestParam String username) {
		return service.changeOperatorEmail(dto, username);
	}

	@PostMapping(ROLE_CREATE)
	Mono<String> createAndSaveRole(@Valid @RequestBody CreatingRoleDto dto, @RequestParam String username) {
		return service.createAndSaveRole(dto, username);
	}
	
	@PostMapping(CHANGE_ROLE)
	Mono<String> changeRole(@Valid @RequestBody ChangeRoleDto dto, @RequestParam String username) {
		return service.changeRole(dto, username);
	}

	@PostMapping(SET_OPERATOR_TO_ROLE)
	public Mono<String> setOperatorToRole(@Valid @RequestBody OperatorToRoleSettingDto dto, @RequestParam String username) {
		log.debug("setOperatorToRole: dto {}, username {}", dto, username);
		return service.setOperatorToRole(dto, username);
	}
	
}

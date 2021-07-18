package warehouse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Mono;
import warehouse.service.interfaces.WarehouseConfiguratorService;
import warehouse.dto.container.*;
import warehouse.dto.operator.*;
import warehouse.dto.product.*;
import warehouse.dto.role.*;

import static warehouse.dto.api.WarehouseConfiguratorApi.*;

import javax.validation.Valid;

@RestController

public class WarehouseConfiguratorController {
	@Autowired
	WarehouseConfiguratorService service;

	@PostMapping(CONTAINER_CREATE)
	Mono<String> createAndSaveContainer(@Valid @RequestBody CreatingContainerDto containerDto) {
		return service.createAndSaveContainer(containerDto);
	}

	@PostMapping(PRODUCT_CREATE)
	Mono<String> createAndSaveContainer(@Valid @RequestBody CreatingProductDto dto) {
		return service.createAndSaveProduct(dto);
	}

	@PostMapping(OPERATOR_CREATE)
	Mono<String> createAndSaveContainer(@Valid @RequestBody CreatingOperatorDto dto) {
		return service.createAndSaveOperator(dto);
	}

	@PostMapping(ROLE_CREATE)
	Mono<String> createAndSaveRole(@Valid @RequestBody CreatingRoleDto dto) {
		return service.createAndSaveRole(dto);
	}

	@PostMapping(SET_OPERATOR_TO_ROLE)
	Mono<String> setOperatorToRole(@Valid @RequestBody OperatorToRoleSettingDto dto) {
		return service.setOperatorToRole(dto);
	}

	@PostMapping(SET_PRODUCT_TO_CONTAINER)
	Mono<String> setProductToContainer(@Valid @RequestBody ProductToContainerSettingDto dto) {
		return service.setProductToContainer(dto);
	}

	@PostMapping(CHANGE_CONTAINER_ADDRESS)
	Mono<String> changeContainerAddress(@Valid @RequestBody ChangeContainerAddressDto dto) {
		return service.changeContainerAddress(dto);
	}

	@PostMapping(CHANGE_PRODUCT_NAME)
	Mono<String> changeProductName(@Valid @RequestBody ChangeProductNameDto dto) {
		return service.changeProductName(dto);
	}

	@PostMapping(SET_TRANSPORT_SUPPLY_TO_PRODUCT)
	Mono<String> setTransportSupply(@Valid @RequestBody TransportSupplySettingDto dto) {
		return service.setTransportSupply(dto);
	}

	@PostMapping(SET_IRREDUCIBLE_BALANCE_TO_PRODUCT)
	Mono<String> setIrreducibleBalance(@Valid @RequestBody IrreducibleBalanceSettingDto dto) {
		return service.setIrreducibleBalance(dto);
	}

	@PostMapping(CHANGE_OPERATOR_NAME)
	Mono<String> changeOperatorName(@Valid @RequestBody ChangeOperatorNameDto dto) {
		return service.changeOperatorName(dto);
	}
	
	@PostMapping(CHANGE_OPERATOR_EMAIL)
	Mono<String> changeOperatorEmail(@Valid @RequestBody ChangeOperatorEmailDto dto) {
		return service.changeOperatorEmail(dto);
	}
	
	@PostMapping(CHANGE_ROLE)
	Mono<String> changeRole(@Valid @RequestBody ChangeRoleDto dto) {
		return service.changeRole(dto);
	}
}

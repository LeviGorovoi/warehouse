package warehouse.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import warehouse.service.interfaces.WarehouseConfiguratorService;
import warehouse.dto.*;
import warehouse.dto.container.*;
import warehouse.dto.enums.*;
import warehouse.dto.operator.*;
import warehouse.dto.product.*;
import warehouse.dto.role.CreatingOperatorRoleDto;
import warehouse.entities.*;

import static warehouse.dto.api.WarehouseConfiguratorApi.*;

import javax.validation.Valid;

@RestController
@Slf4j
public class WarehouseConfiguratorController {
	@Autowired
	WarehouseConfiguratorService service;

	@PutMapping(CONTAINER_CREATE)
	Mono<Void> createAndSaveContainer(@Valid @RequestBody CreatingContainerDto containerDto) {
		return service.createAndSaveContainer(containerDto);		
	}
	@PutMapping(PRODUCT_CREATE)
	Mono<Void> createAndSaveContainer(@Valid @RequestBody CreatingProductDto productDto) {
		return service.createAndSaveProduct(productDto);		
	}
	@PutMapping(OPERATOR_CREATE)
	Mono<Void> createAndSaveContainer(@Valid @RequestBody CreatingOperatorDto operatorDto) {
		return service.createAndSaveOperator(operatorDto);		
	}
	@PutMapping(ROLE_CREATE)
	Mono<Void> createAndSaveOperatorRole(@Valid @RequestBody CreatingOperatorRoleDto operatorRoleDto) {
		return service.createAndSaveOperatorRole(operatorRoleDto);		
	}
}

package warehouse.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import warehouse.service.interfaces.StateBackOficeService;
import warehouse.dto.*;
import warehouse.dto.container.*;
import warehouse.dto.enums.*;
import warehouse.dto.operator.CreatingOperatorDto;
import warehouse.dto.product.*;
import warehouse.dto.role.CreatingOperatorRoleDto;
import warehouse.entities.Container;

import static warehouse.dto.api.WarehouseConfiguratorApi.*;

import javax.validation.Valid;

@RestController
@Slf4j
public class StateBackOficeController {
@Autowired
StateBackOficeService service;
@PutMapping(CONTAINER_CREATE)
Mono<ResponseEntity<Void>> createAndSaveContainer(@Valid @RequestBody CreatingContainerDto containerDto) {
	return service.createAndSaveContainer(containerDto);
}
@PutMapping(PRODUCT_CREATE)
Mono<ResponseEntity<Void>> createAndSaveProduct(@Valid @RequestBody CreatingProductDto productDto) {
	return service.createAndSaveProduct(productDto);
}
@PutMapping(OPERATOR_CREATE)
Mono<ResponseEntity<Void>> createAndSaveOperator(@Valid @RequestBody CreatingOperatorDto operatorDto) {
	return service.createAndSaveOperator(operatorDto);
}
@PutMapping(ROLE_CREATE)
Mono<ResponseEntity<Void>> createAndSaveOperatorRole(@Valid @RequestBody CreatingOperatorRoleDto operatorRoleDto) {
	return service.createAndSaveOperatorRole(operatorRoleDto);
}
}

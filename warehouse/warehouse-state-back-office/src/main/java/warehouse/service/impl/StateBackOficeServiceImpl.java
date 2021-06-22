package warehouse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.*;
import static warehouse.dto.api.WarehouseConfiguratorApi.*;
import lombok.*;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import warehouse.repo.*;
import warehouse.service.interfaces.StateBackOficeService;
import warehouse.dto.container.*;
import warehouse.dto.operator.*;
import warehouse.dto.product.*;
import warehouse.dto.role.*;
import warehouse.entities.*;
//import warehouse.exceptions.*;
import warehouse.exceptions.DuplicatedException;

@Service

@Slf4j
public class StateBackOficeServiceImpl implements StateBackOficeService {
	WebClient client = WebClient.create("http://localhost:9090");

	private Mono<ResponseEntity<Void>> createPutRequest(String uri, Object bodyValue, String errorMessage) {
		return client.put().uri(uri).bodyValue(bodyValue)
				.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).retrieve()
				.onStatus(status -> status.equals(HttpStatus.CONFLICT), e -> {
					throw new DuplicatedException(errorMessage);
				}).toBodilessEntity();
	}

	@Override
	@Transactional
	public Mono<ResponseEntity<Void>> createAndSaveContainer(CreatingContainerDto containerDto) {
		log.debug("Creating container wiht address {}", containerDto.address);
		String errorMessage = String.format("Container with address %s already exist", containerDto.address);
		return createPutRequest(CONTAINER_CREATE, containerDto, errorMessage);
			
	}

	@Override
	public void changeContainerAddress(ChangeContainerAddressDto changeContainerAddressDto) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public String setProductToContainer(ProductToContainerSettingDto containerPurposeSettingDto) {

		return null;
	}

	@Override
	public Mono<ResponseEntity<Void>> createAndSaveProduct(CreatingProductDto productDto) {
		log.debug("Creating product {}", productDto.productName);
		String errorMessage = String.format("Product %s already exist", productDto.productName);
		return createPutRequest(PRODUCT_CREATE, productDto, errorMessage);
	}

	@Override
	public void changeProdactName(ChangeProductNameDto changeProductNameDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public String setTransportSupply(TransportSupplySettingDto transportSupplySettingDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setIrreducibleBalance(IrreducibleBalanceSettingDto irreducibleBalanceSettingDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Mono<ResponseEntity<Void>> createAndSaveOperator(CreatingOperatorDto operatorDto) {
		log.debug("Creating operator {}", operatorDto.operatorName);
		String errorMessage = String.format("Operator %s already exist", operatorDto.operatorName);
		return createPutRequest(OPERATOR_CREATE, operatorDto, errorMessage);

	}

	@Override
	public void changeOperatorName(ChangeOperatorNameDto changeOperatorNameDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public void changeOperatorEmail(ChangeOperatorEmailDto changeOperatorEmailDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public Mono<ResponseEntity<Void>> createAndSaveOperatorRole(CreatingOperatorRoleDto operatorRoleDto) {
		log.debug("Creating role {}", operatorRoleDto.operatorRole);
		String errorMessage = String.format("Operator %s already exist", operatorRoleDto.operatorRole);
		return createPutRequest(ROLE_CREATE, operatorRoleDto, errorMessage);


	}

	@Override
	public void changeRole(ChangeRoleDto changeRoleDto) {
		// TODO Auto-generated method stub

	}

	@Override
	public String setOperatorToRole(OperatorToRoleSettingDto operatorToRoleSettingDto) {
		// TODO Auto-generated method stub
		return null;
	}

}

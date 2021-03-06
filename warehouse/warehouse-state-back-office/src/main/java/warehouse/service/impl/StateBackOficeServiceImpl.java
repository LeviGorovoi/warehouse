
package warehouse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.extern.slf4j.Slf4j;

import static warehouse.dto.api.WarehouseConfiguratorApi.*;

import javax.annotation.PostConstruct;

import reactor.core.publisher.Mono;
import warehouse.service.interfaces.SearchIdByName;
import warehouse.service.interfaces.StateBackOficeService;

import warehouse.dto.container.*;
import warehouse.dto.operator.*;
import warehouse.dto.product.*;
import warehouse.dto.role.*;
import warehouse.loadbalancer.LoadBalancer;
@Slf4j
@Service
public class StateBackOficeServiceImpl implements StateBackOficeService {
	@Autowired
	SearchIdByName searchIdByNameService;
	@Autowired
	LoadBalancer loadbalanser;

	WebClient client;

	@PostConstruct
	void getClient() throws InterruptedException {
		log.debug("@PostConstruct entrance");
		log.debug("loadbalanser.getBaseUrl: {}", loadbalanser.getBaseUrl("warehouse-configurator"));		
		while(loadbalanser.getBaseUrl("warehouse-configurator")==null) {
			log.debug("Sleep entrance");
			Thread.sleep(1000);
		}	
		String baseUri = loadbalanser.getBaseUrl("warehouse-configurator");
		client = WebClient.create(baseUri);
		log.debug("baseUri: {}", baseUri);
	}

	private <T> Mono<String> postRequest(String uri, T bodyValue){
		
		return client.post().uri(uri).bodyValue(bodyValue)
				.exchangeToMono(response -> {
					log.debug("postRequest uri: {}", uri);
			  if (response.statusCode()
					    .equals(HttpStatus.OK)) {
					      return response.bodyToMono(String.class);
					  } else if (response.statusCode()
					    .is4xxClientError()) {
					      return response.bodyToMono(String.class);
					  } else {
					      return response.createException()
					        .flatMap(Mono::error);
					  }
					});				
	}

	@Override
	@Transactional
	public Mono<String> createAndSaveContainer(CreatingContainerDto dto, String username) {
		searchIdByNameService.searchExecutorOperatorIdByUsernameAndSetToDto(username, dto);
		return postRequest(CONTAINER_CREATE, dto);
			
	}

	@Override
	public Mono<String> changeContainerAddress(ChangeContainerAddressDto dto, String username) {
		searchIdByNameService.searchExecutorOperatorIdByUsernameAndSetToDto(username, dto);
		return postRequest(CHANGE_CONTAINER_ADDRESS, dto);
	}

	@Override
	@Transactional
	public Mono<String> setProductToContainer(ProductToContainerSettingDto dto, String username) {
		searchIdByNameService.searchExecutorOperatorIdByUsernameAndSetToDto(username, dto);
		return postRequest(SET_PRODUCT_TO_CONTAINER, dto);
	}

	@Override
	public Mono<String> createAndSaveProduct(CreatingProductDto dto, String username) {
		searchIdByNameService.searchExecutorOperatorIdByUsernameAndSetToDto(username, dto);
		return postRequest(PRODUCT_CREATE, dto);
	}

	@Override
	public Mono<String> changeProdactName(ChangeProductNameDto dto, String username) {
		searchIdByNameService.searchExecutorOperatorIdByUsernameAndSetToDto(username, dto);
		return postRequest(CHANGE_PRODUCT_NAME, dto);
	}

	@Override
	public Mono<String> setTransportSupply(TransportSupplySettingDto dto, String username) {
		searchIdByNameService.searchExecutorOperatorIdByUsernameAndSetToDto(username, dto);
		return postRequest(SET_TRANSPORT_SUPPLY_TO_PRODUCT, dto);
	}

	@Override
	public Mono<String> setIrreducibleBalance(IrreducibleBalanceSettingDto dto, String username) {
		searchIdByNameService.searchExecutorOperatorIdByUsernameAndSetToDto(username, dto);
		return postRequest(SET_IRREDUCIBLE_BALANCE_TO_PRODUCT, dto);
	}

	@Override
	public Mono<String> createAndSaveOperator(CreatingOperatorDto dto, String username) {
		searchIdByNameService.searchExecutorOperatorIdByUsernameAndSetToDto(username, dto);
		return postRequest(OPERATOR_CREATE, dto);
	}

	@Override
	public Mono<String> changeOperatorName(ChangeOperatorNameDto dto, String username) {
		searchIdByNameService.searchExecutorOperatorIdByUsernameAndSetToDto(username, dto);
		return postRequest(CHANGE_OPERATOR_NAME, dto);
	}

	@Override
	public Mono<String> changeOperatorEmail(ChangeOperatorEmailDto dto, String username) {
		searchIdByNameService.searchExecutorOperatorIdByUsernameAndSetToDto(username, dto);
		return postRequest(CHANGE_OPERATOR_EMAIL, dto);
	}

	@Override
	public Mono<String> createAndSaveRole(CreatingRoleDto dto, String username) {
		searchIdByNameService.searchExecutorOperatorIdByUsernameAndSetToDto(username, dto);
		return postRequest(ROLE_CREATE, dto);
	}

	@Override
	public Mono<String> changeRole(ChangeRoleDto dto, String username) {
		searchIdByNameService.searchExecutorOperatorIdByUsernameAndSetToDto(username, dto);
		return postRequest(CHANGE_ROLE, dto);
	}

	@Override
	public Mono<String> setOperatorToRole(OperatorToRoleSettingDto dto, String username) {
		searchIdByNameService.searchExecutorOperatorIdByUsernameAndSetToDto(username, dto);
		return postRequest(SET_OPERATOR_TO_ROLE, dto);
	}

}

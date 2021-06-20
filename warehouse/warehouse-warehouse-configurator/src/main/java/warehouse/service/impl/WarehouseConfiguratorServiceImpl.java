package warehouse.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.*;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import warehouse.repo.*;
import warehouse.service.interfaces.WarehouseConfiguratorService;
import warehouse.dto.container.*;
import warehouse.dto.operator.*;
import warehouse.dto.product.*;
import warehouse.dto.role.*;
import warehouse.entities.*;
import warehouse.exceptions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service

@Slf4j
public class WarehouseConfiguratorServiceImpl implements WarehouseConfiguratorService {
	@Value("${app-binding-name-docs:docs-out-0}")
	String dtoForDocBindingName;

	ContainerWarehouseConfiguratorRepo containerWarehouseConfiguratorRepo;
	OperatorWarehouseConfiguratorRepo operatorWarehouseConfiguratorRepo;
	OperatorRoleWarehouseConfiguratorRepo operatorRoleWarehouseConfiguratorRepo;
	ProductWarehouseConfiguratorRepo productWarehouseConfiguratorRepo;
	StreamBridge streamBridge;
	ObjectMapper objectMapper;

	public WarehouseConfiguratorServiceImpl(ContainerWarehouseConfiguratorRepo containerWarehouseConfiguratorRepo,
			OperatorWarehouseConfiguratorRepo operatorWarehouseConfiguratorRepo,
			OperatorRoleWarehouseConfiguratorRepo operatorRoleWarehouseConfiguratorRepo,
			ProductWarehouseConfiguratorRepo productWarehouseConfiguratorRepo, StreamBridge streamBridge,
			ObjectMapper objectMapper) {
		super();
		this.containerWarehouseConfiguratorRepo = containerWarehouseConfiguratorRepo;
		this.operatorWarehouseConfiguratorRepo = operatorWarehouseConfiguratorRepo;
		this.operatorRoleWarehouseConfiguratorRepo = operatorRoleWarehouseConfiguratorRepo;
		this.productWarehouseConfiguratorRepo = productWarehouseConfiguratorRepo;
		this.streamBridge = streamBridge;
		this.objectMapper = objectMapper;
	}

	private <T, ID> void saveMethod(JpaRepository<T, ID> repo, T newObj,
			String errorMessage) {
		try {
			repo.save(newObj);
			log.debug("newObj by {} saved", newObj.getClass() );
		} catch (DataIntegrityViolationException e) {
			throw new DuplicatedException(errorMessage);
		}
	}

	@Override
	@Transactional
	public  Mono<Void> createAndSaveContainer(CreatingContainerDto containerDto) {
		log.debug("the containerDto with address {}  recieved by thread {} ", containerDto.address, Thread.currentThread().getName());
		return Mono.create(s->{
			Container newContainer = Container.builder().address(containerDto.address).build();
			String errorMessage = String.format("Container with address %s already exist",
					containerDto.address);
			saveMethod(containerWarehouseConfiguratorRepo, newContainer, errorMessage);
			log.debug("saved container with address {} by thread {} ", containerDto.address, Thread.currentThread().getName());
			s.success();
		});

	}

	@Override
	@Transactional
	public void changeContainerAddress(ChangeContainerAddressDto changeContainerAddressDto) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public String setProductToContainer(ProductToContainerSettingDto containerPurposeSettingDto) {
		String dtoForDocJson = "";
		// containerId productId передаются из бэк-офиса. Там они появляются ккогда
		// клиент выбирает через фильтры по именам.
		// контроль наличия имен там
		// containerId productIds are passed from the back office. There they appear
		// when the client
		// selects through name filters. Check if names exist there
		Product product = productWarehouseConfiguratorRepo.findById(containerPurposeSettingDto.productId).orElse(null);
		// на случай, если вдруг будет удалено в процессе транзакции
		// in case it is suddenly deleted during the transaction
		if (product == null) {
			throw new NotFoundException(String.format("No product with id %d", containerPurposeSettingDto.productId));
		}

		containerWarehouseConfiguratorRepo.setProductToContainer(containerPurposeSettingDto.containerId, product);
		ProductToContainerSettingDtoForDoc dtoForDoc = ProductToContainerSettingDtoForDoc.builder()
				.productToContainerSettingDate(null).containerId(containerPurposeSettingDto.containerId)
				.productId(containerPurposeSettingDto.productId).creatingMethod(null).operatorId(0).build();
		// TODO definition creatingMethod and operatorId
		try {
			dtoForDocJson = objectMapper.writeValueAsString(dtoForDoc);
		} catch (JsonProcessingException e) {
			log.debug("ProductToContainerSettingDtoForDoc was not serialized");
		}
		streamBridge.send(dtoForDocBindingName, dtoForDocJson);
		return null;
	}

	@Override
	@Transactional
	public Mono<Void> createAndSaveProduct(CreatingProductDto productDto) {
		log.debug("the productDto {} recieved by thread {} ", productDto.productName, Thread.currentThread().getName());
		return Mono.create(s->{
			Product newProduct = Product.builder().numberInContainer(productDto.numberInContainer)
				.productName(productDto.productName).build();
		String errorMessage = String.format("Product %s already exist", productDto.productName);
		saveMethod(productWarehouseConfiguratorRepo, newProduct, errorMessage);
		log.debug("saved product {} by thread {} ",productDto.productName, Thread.currentThread().getName());
		s.success();
		});
	}

	@Override
	@Transactional
	public void changeProductName(ChangeProductNameDto changeProductNameDto) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public String setTransportSupply(TransportSupplySettingDto transportSupplySettingDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public String setIrreducibleBalance(IrreducibleBalanceSettingDto irreducibleBalanceSettingDto) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public Mono<Void> createAndSaveOperator(CreatingOperatorDto operatorDto) {
		log.debug("the operatorDto recieved by thread {} ", Thread.currentThread().getName());
		return Mono.create(s->{
		Operator newOperator = Operator.builder().operatorName(operatorDto.operatorName)
				.operatorEmail(operatorDto.operatorEmail).build();
		String errorMessage = String.format("Operator %s already exist", operatorDto.operatorName);
		saveMethod(operatorWarehouseConfiguratorRepo, newOperator, errorMessage);
		log.debug("saved operator {} by thread {} ",operatorDto.operatorName, Thread.currentThread().getName());
		s.success();
		});
	}

	@Override
	@Transactional
	public void changeOperatorName(ChangeOperatorNameDto changeOperatorNameDto) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public void changeOperatorEmail(ChangeOperatorEmailDto changeOperatorEmailDto) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public Mono<Void> createAndSaveOperatorRole(CreatingOperatorRoleDto operatorRoleDto) {
		log.debug("the operatorRoleDto recieved by thread {} ", Thread.currentThread().getName());
		return Mono.create(s->{
		OperatorRole newOperatorRole = OperatorRole.builder().role(operatorRoleDto.operatorRole).build();
		String errorMessage = String.format("Role %s already exist", operatorRoleDto.operatorRole);
		saveMethod(operatorRoleWarehouseConfiguratorRepo, newOperatorRole, errorMessage);
		log.debug("saved operatorRole {} by thread {} ",operatorRoleDto.operatorRole, Thread.currentThread().getName());
		s.success();
		});
	}

	@Override
	@Transactional
	public void changeRole(ChangeRoleDto changeRoleDto) {
		// TODO Auto-generated method stub

	}

	@Override
	@Transactional
	public String setOperatorToRole(OperatorToRoleSettingDto operatorToRoleSettingDto) {
		// TODO Auto-generated method stub
		return null;
	}

}

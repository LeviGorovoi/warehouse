package warehouse.service.impl;

import java.util.function.BiFunction;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import warehouse.repo.*;
import warehouse.service.interfaces.WarehouseConfiguratorService;
import warehouse.dto.JsonForJournalingDto;
import warehouse.dto.ParentDto;
import warehouse.dto.container.*;
import warehouse.dto.operator.*;
import warehouse.dto.product.*;
import warehouse.dto.role.*;
import warehouse.entities.*;
import warehouse.exceptions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service(value = "wcs")
@Slf4j
public class WarehouseConfiguratorServiceImpl implements WarehouseConfiguratorService {
	@Value("${app-binding-name-docs:docs-out-0}")
	String dtoForDocBindingName;
	@Resource(name = "wcs")
	WarehouseConfiguratorServiceImpl serviceImpl;

	ContainerWarehouseConfiguratorRepo containerRepo;
	OperatorWarehouseConfiguratorRepo operatorRepo;
	RoleWarehouseConfiguratorRepo roleRepo;
	ProductWarehouseConfiguratorRepo productRepo;
	StreamBridge streamBridge;
	ObjectMapper objectMapper;

	public WarehouseConfiguratorServiceImpl(ContainerWarehouseConfiguratorRepo containerRepo,
			OperatorWarehouseConfiguratorRepo operatorRepo, RoleWarehouseConfiguratorRepo roleRepo,
			ProductWarehouseConfiguratorRepo productRepo, StreamBridge streamBridge, ObjectMapper objectMapper) {
		super();
		this.containerRepo = containerRepo;
		this.operatorRepo = operatorRepo;
		this.roleRepo = roleRepo;
		this.productRepo = productRepo;
		this.streamBridge = streamBridge;
		this.objectMapper = objectMapper;
	}

	private <T, ID> void saveMethod(ParentDto parentDto, JpaRepository<T, ID> repo, T newObj, String errorMessage) {
		try {
			repo.save(newObj);
			sendDocDtoToNonsqlDB(parentDto);
			log.debug("saveMethod; dto {} by {} sent to NonSQLDB", parentDto, parentDto.getClass());
		} catch (DataIntegrityViolationException e) {
			throw new DuplicatedException(errorMessage);
		}
	}

	@Transactional
	public <T, ID> void makeSetting(ParentDto parentDto, ID graftId, long recipientId, JpaRepository<T, ID> graftRepo,
			String notFoundGraftMessage, String notFoundRecipientMessage,
			BiFunction<Long, T, Integer> settingFunction) {
		T graft = graftRepo.findById(graftId).orElseThrow(() -> new NotFoundException(notFoundGraftMessage));
		if (settingFunction.apply(recipientId, graft) == 0) {
			throw new NotFoundException(notFoundRecipientMessage);
		}
		sendDocDtoToNonsqlDB(parentDto);
	}

	@Transactional
	public <T> void makeUpdate(ParentDto parentDto, long recipientId, T newParametr, String notFoundRecipientMessage,
			BiFunction<Long, T, Integer> settingFunction) {
		if (settingFunction.apply(recipientId, newParametr) == 0) {
			throw new NotFoundException(notFoundRecipientMessage);
		}
		sendDocDtoToNonsqlDB(parentDto);
	}

	private <T> void sendDocDtoToNonsqlDB(T docDto) {
		JsonForJournalingDto jsonForJournalingDto = new JsonForJournalingDto();
		try {
			String dtoForDocJson = objectMapper.writeValueAsString(docDto);
			jsonForJournalingDto.setClassName(docDto.getClass().getName());
			jsonForJournalingDto.setJsonForJournaling(dtoForDocJson);
			log.debug("sendDocDtoToNonsqlDB: dtoForDocJson {}", dtoForDocJson);
		} catch (JsonProcessingException e) {
			log.debug("Object was not serialized");
		}

		streamBridge.send(dtoForDocBindingName, jsonForJournalingDto);
	}

	@Override
	public Mono<String> createAndSaveContainer(CreatingContainerDto dto) {
		log.debug("createAndSaveContainer: the dto {} recieved", dto.toString());
		return Mono.create(s -> {
			Container newContainer = Container.builder().address(dto.getAddress()).build();
			String errorMessage = String.format("Container with address %s already exist", dto.getAddress());
			String successMessage = String.format("the container with address %s created successfully",
					dto.getAddress());
			saveMethod(dto, containerRepo, newContainer, errorMessage);
			log.debug("createAndSaveContainer: " + successMessage);
			s.success(successMessage);
		});

	}

	@Override
	public Mono<String> changeContainerAddress(ChangeContainerAddressDto dto) {
		log.debug("changeContainerAddress: the dto {} recieved", dto.toString());
		String notFoundRecipientMessage = String.format("container with id %s not found", dto.getContainerId());
		String successMessage = String.format("the address of the container with id %s changed to %s",
				dto.getContainerId(), dto.getNewAddress());
		BiFunction<Long, String, Integer> settingFunction = (containerId, newAddress) -> containerRepo
				.changeContainerAddress(containerId, newAddress);
		return Mono.create(s -> {
			serviceImpl.makeUpdate(dto, dto.getContainerId(), dto.getNewAddress(), notFoundRecipientMessage,
					settingFunction);
			log.debug("changeContainerAddress: " + successMessage);
			s.success(successMessage);
		});
	}

	// containerId productId передаются из бэк-офиса. Там они появляются когда
	// клиент выбирает через фильтры по именам.
	// контроль наличия имен там
	// containerId productIds are passed from the back office. There they appear
	// when the client
	// selects through name filters. Check if names exist there
	@Override
	public Mono<String> setProductToContainer(ProductToContainerSettingDto dto) {
		log.debug("setOperatorToRole: the dto {} recieved", dto.toString());
		String notFoundGraftMessage = String.format("No product with id %d", dto.getProductId());
		String notFoundRecipientMessage = String.format("container with id %s not found", dto.getContainerId());
		String successMessage = String.format("the product %s has been successfully assigned to the container %s",
				dto.getProductId(), dto.getContainerId());
		BiFunction<Long, Product, Integer> settingFunction = (containerId, product) -> containerRepo
				.setProductToContainer(containerId, product);
		return Mono.create(s -> {
			serviceImpl.makeSetting(dto, dto.getProductId(), dto.getContainerId(), productRepo, notFoundGraftMessage,
					notFoundRecipientMessage, settingFunction);
			log.debug("setOperatorToRole: " + successMessage);
			s.success(successMessage);
		});
	}

	@Override
	public Mono<String> createAndSaveProduct(CreatingProductDto dto) {
		log.debug("createAndSaveProduct: the dto {} recieved", dto.toString());
		return Mono.create(s -> {
			Product newProduct = Product.builder().numberInContainer(dto.getNumberInContainer())
					.productName(dto.getProductName()).build();
			String errorMessage = String.format("Product %s already exist", dto.getProductName());
			String successMessage = String.format("the product %s created successfully", dto.getProductName());
			saveMethod(dto, productRepo, newProduct, errorMessage);
			log.debug("createAndSaveProduct: " + successMessage);
			s.success(successMessage);
		});
	}

	@Override
	public Mono<String> changeProductName(ChangeProductNameDto dto) {
		log.debug("changeProductName: the dto {} recieved", dto.toString());
		String notFoundRecipientMessage = String.format("product with id %s not found", dto.getProductId());
		String successMessage = String.format("the name of the product with id %s was changed to %s",
				dto.getProductId(), dto.getNewProductName());
		BiFunction<Long, String, Integer> settingFunction = (productId, newProductName) -> productRepo
				.changeProductName(productId, newProductName);
		return Mono.create(s -> {
			serviceImpl.makeUpdate(dto, dto.getProductId(), dto.getNewProductName(), notFoundRecipientMessage,
					settingFunction);
			log.debug("changeProductName: " + successMessage);
			s.success(successMessage);
		});

	}

	@Override
	public Mono<String> setTransportSupply(TransportSupplySettingDto dto) {
		log.debug("setTransportSupply: the dto {} recieved", dto.toString());
		String notFoundRecipientMessage = String.format("product with id %s not found", dto.getProductId());
		String successMessage = String.format("the transport supply in %s days was settled to product with id %s",
				dto.getTransportSupply(), dto.getProductId());
		BiFunction<Long, Integer, Integer> settingFunction = (productId, transportSupply) -> productRepo
				.setTransportSupply(productId, transportSupply);
		return Mono.create(s -> {
			serviceImpl.makeUpdate(dto, dto.getProductId(), dto.getTransportSupply(), notFoundRecipientMessage,
					settingFunction);
			log.debug("setTransportSupply: " + successMessage);
			s.success(successMessage);
		});
	}

	@Override
	public Mono<String> setIrreducibleBalance(IrreducibleBalanceSettingDto dto) {
		log.debug("setIrreducibleBalance: the dto {} recieved", dto.toString());
		String notFoundRecipientMessage = String.format("product with id %s not found", dto.getProductId());
		String successMessage = String.format("the irreducible balance in %s items was settled to product with id %s",
				dto.getIrreducibleBalance(), dto.getProductId());
		BiFunction<Long, Integer, Integer> settingFunction = (productId, irreducibleBalanceAmount) -> productRepo
				.setIrreducibleBalance(productId, irreducibleBalanceAmount);
		return Mono.create(s -> {
			serviceImpl.makeUpdate(dto, dto.getProductId(), dto.getIrreducibleBalance(), notFoundRecipientMessage,
					settingFunction);
			log.debug("setIrreducibleBalance: " + successMessage);
			s.success(successMessage);
		});
	}

	@Override
	public Mono<String> createAndSaveOperator(CreatingOperatorDto dto) {
		log.debug("createAndSaveOperator: the dto {} recieved", dto.toString());
		return Mono.create(s -> {
			Operator newOperator = Operator.builder().operatorName(dto.getOperatorName())
					.operatorEmail(dto.getOperatorEmail()).build();
			String errorMessage = String.format("Operator %s already exist", dto.getOperatorName());
			String successMessage = String.format("the operator %s created successfully", dto.getOperatorName());
			saveMethod(dto, operatorRepo, newOperator, errorMessage);
			log.debug("createAndSaveOperator: " + successMessage);
			s.success(successMessage);
		});
	}

	@Override
	public Mono<String> changeOperatorName(ChangeOperatorNameDto dto) {
		log.debug("changeOperatorName: the dto {} recieved", dto.toString());
		String notFoundRecipientMessage = String.format("operator with id %s not found", dto.getOperatorId());
		String successMessage = String.format("the name of the operator with id %s was changed to %s",
				dto.getOperatorId(), dto.getNewOperatorName());
		BiFunction<Long, String, Integer> settingFunction = (operatorId, newOperatorName) -> operatorRepo
				.changeOperatorName(operatorId, newOperatorName);
		return Mono.create(s -> {
			serviceImpl.makeUpdate(dto, dto.getOperatorId(), dto.getNewOperatorName(), notFoundRecipientMessage,
					settingFunction);
			log.debug("changeOperatorName: " + successMessage);
			s.success(successMessage);
		});

	}

	@Override
	public Mono<String> changeOperatorEmail(ChangeOperatorEmailDto dto) {
		log.debug("changeOperatorEmail: the dto {} recieved", dto.toString());
		String notFoundRecipientMessage = String.format("operator with id %s not found", dto.getOperatorId());
		String successMessage = String.format("the email of the operator with id %s was changed to %s",
				dto.getOperatorId(), dto.getNewOperatorEmail());
		BiFunction<Long, String, Integer> settingFunction = (operatorId, newOperatorName) -> operatorRepo
				.changeOperatorEmail(operatorId, newOperatorName);
		return Mono.create(s -> {
			serviceImpl.makeUpdate(dto, dto.getOperatorId(), dto.getNewOperatorEmail(), notFoundRecipientMessage,
					settingFunction);
			log.debug("changeOperatorEmail: " + successMessage);
			s.success(successMessage);
		});

	}

	@Override
	public Mono<String> createAndSaveRole(CreatingRoleDto dto) {
		log.debug("createAndSaveRole: the dto {} recieved", dto.toString());
		return Mono.create(s -> {
			Role newRole = Role.builder().role(dto.getRole()).build();
			String errorMessage = String.format("Role %s already exist", dto.getRole());
			String successMessage = String.format("the role %s created successfully", dto.getRole());
			saveMethod(dto, roleRepo, newRole, errorMessage);
			log.debug("createAndSaveRole: " + successMessage);
			s.success(successMessage);
		});
	}

	@Override
	public Mono<String> changeRole(ChangeRoleDto dto) {
		log.debug("changeRole: the dto {} recieved", dto.toString());
		String notFoundRecipientMessage = String.format("role with id %s not found", dto.getRoleId());
		String successMessage = String.format("the role with id %s was changed to %s", dto.getRoleId(),
				dto.getNewRole());
		BiFunction<Long, String, Integer> settingFunction = (roleId, newRole) -> roleRepo.changeRole(roleId, newRole);
		return Mono.create(s -> {
			serviceImpl.makeUpdate(dto, dto.getRoleId(), dto.getNewRole(), notFoundRecipientMessage, settingFunction);
			log.debug("changeRole: " + successMessage);
			s.success(successMessage);
		});
	}

	@Override
	public Mono<String> setOperatorToRole(OperatorToRoleSettingDto dto) {
		log.debug("setOperatorToRole: the dto {} recieved", dto.toString());
		String notFoundGraftMessage = String.format("No operator with id %d", dto.getOperatorId());
		String notFoundRecipientMessage = String.format("role with id %s not found", dto.getRoleId());
		String successMessage = String.format("the %s role has been successfully assigned to the operator %s",
				dto.getRoleId(), dto.getOperatorId());
		BiFunction<Long, Operator, Integer> settingFunction = (roleId, operator) -> roleRepo.setOperatorToRole(roleId,
				operator);
		return Mono.create(s -> {
			serviceImpl.makeSetting(dto, dto.getOperatorId(), dto.getRoleId(), operatorRepo, notFoundGraftMessage,
					notFoundRecipientMessage, settingFunction);
			log.debug("setOperatorToRole: " + successMessage);
			s.success(successMessage);
		});
	}

}

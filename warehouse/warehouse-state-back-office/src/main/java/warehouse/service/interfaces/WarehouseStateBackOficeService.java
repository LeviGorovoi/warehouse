package warehouse.service.interfaces;

import org.springframework.http.ResponseEntity;

import reactor.core.publisher.Mono;
import warehouse.dto.container.*;
import warehouse.dto.operator.*;
import warehouse.dto.product.*;
import warehouse.dto.role.*;
import warehouse.entities.Container;


public interface WarehouseStateBackOficeService {
Mono<ResponseEntity<Void>> createAndSaveContainer(CreatingContainerDto containerDto);
void changeContainerAddress(ChangeContainerAddressDto changeContainerAddressDto);
String setProductToContainer(ProductToContainerSettingDto containerPurposeSettingDto);

Mono<ResponseEntity<Void>> createAndSaveProduct (CreatingProductDto productDto);
void changeProdactName (ChangeProductNameDto changeProductNameDto);
String setTransportSupply(TransportSupplySettingDto transportSupplySettingDto);
String setIrreducibleBalance(IrreducibleBalanceSettingDto irreducibleBalanceSettingDto);

Mono<ResponseEntity<Void>> createAndSaveOperator (CreatingOperatorDto operatorDto);
void changeOperatorName (ChangeOperatorNameDto changeOperatorNameDto);
void changeOperatorEmail (ChangeOperatorEmailDto changeOperatorEmailDto);

Mono<ResponseEntity<Void>> createAndSaveOperatorRole(CreatingOperatorRoleDto operatorRoleDto);
void changeRole (ChangeRoleDto changeRoleDto);
String setOperatorToRole(OperatorToRoleSettingDto operatorToRoleSettingDto);
}
 
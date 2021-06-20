package warehouse.service.interfaces;

import reactor.core.publisher.Mono;
import warehouse.dto.container.*;
import warehouse.dto.operator.*;
import warehouse.dto.product.*;
import warehouse.dto.role.*;
import warehouse.entities.Container;


public interface WarehouseConfiguratorService {
Mono<Void> createAndSaveContainer(CreatingContainerDto containerDto);
void changeContainerAddress(ChangeContainerAddressDto changeContainerAddressDto);
String setProductToContainer(ProductToContainerSettingDto containerPurposeSettingDto);

Mono<Void> createAndSaveProduct (CreatingProductDto productDto);
void changeProductName (ChangeProductNameDto changeProductNameDto);
String setTransportSupply(TransportSupplySettingDto transportSupplySettingDto);
String setIrreducibleBalance(IrreducibleBalanceSettingDto irreducibleBalanceSettingDto);

Mono<Void> createAndSaveOperator (CreatingOperatorDto operatorDto);
void changeOperatorName (ChangeOperatorNameDto changeOperatorNameDto);
void changeOperatorEmail (ChangeOperatorEmailDto changeOperatorEmailDto);

Mono<Void> createAndSaveOperatorRole(CreatingOperatorRoleDto operatorRoleDto);
void changeRole (ChangeRoleDto changeRoleDto);
String setOperatorToRole(OperatorToRoleSettingDto operatorToRoleSettingDto);
}
 
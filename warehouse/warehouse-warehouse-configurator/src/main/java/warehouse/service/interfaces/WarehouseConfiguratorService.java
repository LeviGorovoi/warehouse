package warehouse.service.interfaces;

import reactor.core.publisher.Mono;
import warehouse.dto.container.*;
import warehouse.dto.operator.*;
import warehouse.dto.product.*;
import warehouse.dto.role.*;


public interface WarehouseConfiguratorService {
Mono<String> createAndSaveContainer(CreatingContainerDto containerDto);
Mono<String> changeContainerAddress(ChangeContainerAddressDto changeContainerAddressDto);
Mono<String> setProductToContainer(ProductToContainerSettingDto containerPurposeSettingDto);

Mono<String> createAndSaveProduct (CreatingProductDto productDto);
Mono<String> changeProductName (ChangeProductNameDto changeProductNameDto);
Mono<String> setTransportSupply(TransportSupplySettingDto transportSupplySettingDto);
Mono<String> setIrreducibleBalance(IrreducibleBalanceSettingDto irreducibleBalanceSettingDto);

Mono<String> createAndSaveOperator (CreatingOperatorDto operatorDto);
Mono<String> changeOperatorName (ChangeOperatorNameDto changeOperatorNameDto);
Mono<String> changeOperatorEmail (ChangeOperatorEmailDto changeOperatorEmailDto);

Mono<String> createAndSaveRole(CreatingRoleDto roleDto);
Mono<String> changeRole (ChangeRoleDto changeRoleDto);
Mono<String> setOperatorToRole(OperatorToRoleSettingDto operatorToRoleSettingDto);
}
 
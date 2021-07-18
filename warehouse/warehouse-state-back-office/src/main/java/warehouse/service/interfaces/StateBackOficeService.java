package warehouse.service.interfaces;


import reactor.core.publisher.Mono;
import warehouse.dto.container.*;
import warehouse.dto.operator.*;
import warehouse.dto.product.*;
import warehouse.dto.role.*;


public interface StateBackOficeService {
Mono<String> createAndSaveContainer(CreatingContainerDto dto, String username);
Mono<String> changeContainerAddress(ChangeContainerAddressDto dto, String username);
Mono<String> setProductToContainer(ProductToContainerSettingDto dto, String username);

Mono<String> createAndSaveProduct(CreatingProductDto dto, String username);
Mono<String> changeProdactName(ChangeProductNameDto dto, String username);
Mono<String> setTransportSupply(TransportSupplySettingDto dto, String username);
Mono<String> setIrreducibleBalance(IrreducibleBalanceSettingDto dto, String username);

Mono<String> createAndSaveOperator(CreatingOperatorDto dto, String username);
Mono<String> changeOperatorName(ChangeOperatorNameDto dto, String username);
Mono<String> changeOperatorEmail(ChangeOperatorEmailDto dto, String username);

Mono<String> createAndSaveRole(CreatingRoleDto dto, String username);
Mono<String> changeRole(ChangeRoleDto dto, String username);
Mono<String> setOperatorToRole(OperatorToRoleSettingDto dto, String username);
}
 
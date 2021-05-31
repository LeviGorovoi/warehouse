package warehouse;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;

import warehouse.doc.*;
import warehouse.dto.CreatingMethodEnum;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= {ContainerPurposeSettingRepo.class, InventoryRepo.class, MovementDocRepo.class,
		OrderRepo.class, RoleSettingRepo.class, TransportSupplySettingRepo.class})
@EnableAutoConfiguration 
@AutoConfigureDataMongo
public class DocsTests {
@Autowired
ContainerPurposeSettingRepo containerPurposeSettingRepo;
@Autowired
InventoryRepo inventoryRepo;
@Autowired
MovementDocRepo movementDocRepo;
@Autowired
OrderRepo orderRepo;
@Autowired
RoleSettingRepo roleSettingRepo;
@Autowired
TransportSupplySettingRepo transportSupplySettingRepo;
@Test
void docsTest() {
	ContainerPurposeSetting containerPurposeSetting = ContainerPurposeSetting.builder().containerId(1)
			.containerPurposeSettingDate(LocalDateTime.of(2021, 12, 31, 12, 0))
			.creatingMethod(CreatingMethodEnum.BY_OPERATOR)
			.documentDateTime(LocalDateTime.of(2021, 05, 30, 23, 33))
			.operatorId(1).productId(0)
			.build();
	
	containerPurposeSettingRepo.save(containerPurposeSetting).block();
	ContainerPurposeSetting actual = containerPurposeSettingRepo.findAll().blockFirst();
	assertEquals(containerPurposeSetting, actual);
}
}

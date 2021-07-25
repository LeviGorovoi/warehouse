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

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes= {ContainerPurposeSettingRepo.class, InventoryRepo.class, MovementDocRepo.class,
		OrderRepo.class, OperatorToRoleSettingRepo.class, TransportSupplySettingRepo.class})
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
TransportSupplySettingRepo transportSupplySettingRepo;
@Test
void docsTest() {
}
}

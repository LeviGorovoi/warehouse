package warehouse;


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
@ContextConfiguration(classes= { InventoryRepo.class, MovementDocRepo.class,
		OrderRepo.class})
@EnableAutoConfiguration 
@AutoConfigureDataMongo
public class DocsTests {

@Autowired
InventoryRepo inventoryRepo;
@Autowired
MovementDocRepo movementDocRepo;
@Autowired
OrderRepo orderRepo;

@Test
void docsTest() {
}
}

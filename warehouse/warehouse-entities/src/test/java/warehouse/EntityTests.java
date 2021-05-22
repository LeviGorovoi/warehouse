package warehouse;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;


import warehouse.entities.Container;
import warehouse.entities.Product;
import warehouse.entities.RemainderInContainer;

@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration
@ContextConfiguration(classes= {ContainerRepo.class, ProductRepo.class, RemainderInContainerRepo.class})
public class EntityTests {
	@Autowired
	ContainerRepo containerRepo;
	@Autowired
	ProductRepo productRepo;
	@Autowired
	RemainderInContainerRepo remainderInContainerRepo;
	
	@Test
	void EntitiesTest () {
		Product product = Product.builder().irreducibleBalance(0).numberInContainer(10).productName("asd").transportStock(0).build();
		productRepo.save(product);
		Container container = new Container("123", product);
		containerRepo.save(container);
		RemainderInContainer remainderInContainer = RemainderInContainer.builder().container(container)
				.product(product).receiptDate(LocalDateTime.of(1920, 2, 3, 4, 0)).remainder(20).build();
		remainderInContainerRepo.save(remainderInContainer);
		List<RemainderInContainer> res = remainderInContainerRepo.findAll();
		assertEquals(1, res.size());
		assertEquals(remainderInContainer, res.get(0));
		remainderInContainerRepo.save(remainderInContainer);
		res = remainderInContainerRepo.findAll();
		assertEquals(1, res.size());
		RemainderInContainer remainderInContainer1 = RemainderInContainer.builder().container(container)
				.product(product).receiptDate(LocalDateTime.of(1920, 3, 3, 4, 0)).remainder(20).build();
		remainderInContainerRepo.save(remainderInContainer1);
		res = remainderInContainerRepo.findAll();
		assertEquals(2, res.size());
		
	}
}

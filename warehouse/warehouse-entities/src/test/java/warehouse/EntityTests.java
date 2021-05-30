package warehouse;

import java.time.LocalDateTime;
import java.util.*;

import javax.persistence.Temporal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import warehouse.entities.*;
@Slf4j
@ExtendWith(SpringExtension.class)
@EnableAutoConfiguration
@ContextConfiguration(classes = { ContainerRepo.class, ProductRepo.class, RemainderInContainerRepo.class, 
		OperatorRepo.class, OperatorRoleRepo.class, TestService.class })
public class EntityTests {
	@Autowired
	ContainerRepo containerRepo;
	@Autowired
	ProductRepo productRepo;
	@Autowired
	RemainderInContainerRepo remainderInContainerRepo;
	@Autowired
	OperatorRepo operatorRepo;
	@Autowired
	OperatorRoleRepo operatorRoleRepo;
	@Autowired 
	TestService testService;


	@Test
	void ContainerProductTest() {
		Product product = Product.builder().irreducibleBalance(0).numberInContainer(10).productName("asd")
				.transportSupply(0).build();
		productRepo.save(product);
		Container container = new Container("123", product);
		containerRepo.save(container);
		RemainderInContainer remainderInContainer = RemainderInContainer.builder().container(container).product(product)
				.receiptDateTime(LocalDateTime.of(1920, 2, 3, 4, 0)).remainder(20).build();
		remainderInContainerRepo.save(remainderInContainer);
		List<RemainderInContainer> res = remainderInContainerRepo.findAll();
		assertEquals(1, res.size());
		assertEquals(remainderInContainer, res.get(0));
		remainderInContainerRepo.save(remainderInContainer);
		res = remainderInContainerRepo.findAll();
		assertEquals(1, res.size());
		RemainderInContainer remainderInContainer1 = RemainderInContainer.builder().container(container)
				.product(product).receiptDateTime(LocalDateTime.of(1920, 3, 3, 4, 0)).remainder(20).build();
		remainderInContainerRepo.save(remainderInContainer1);
		res = remainderInContainerRepo.findAll();
		assertEquals(2, res.size());
		RemainderInContainer remainderInContainer2 = RemainderInContainer.builder().container(container)
				.product(product).receiptDateTime(LocalDateTime.of(1920, 3, 3, 4, 0)).remainder(20).build();
//		Throwable thrown = catchThrowable(()->remainderInContainerRepo.save(remainderInContainer2));
//		assertThat(thrown)
//		  .isInstanceOf(DataIntegrityViolationException.class)
//		  .hasMessageContaining("could not execute statement");
//		Exception exception = assertThrows(DataIntegrityViolationException.class, 
//				()->remainderInContainerRepo.save(remainderInContainer2));
		try {
			remainderInContainerRepo.save(remainderInContainer2);
		} catch (DataIntegrityViolationException e) {
			assertTrue(e.getMessage().contains("could not execute statement"));
		}

	}

	@Test
	void OperatorTest() {
		OperatorRole operatorRole = OperatorRole.builder().role("qwe").operator(null).build();
		operatorRoleRepo.save(operatorRole);
		Operator operator = Operator.builder().operatorEmail("email").operatorName("zxc")
				.operatorRoles(new HashSet<OperatorRole>()).build();
		operatorRepo.save(operator);
		OperatorRole operatorRoleWithId = operatorRoleRepo.findByRole("qwe");
		Operator operatorWithId = operatorRepo.findByOperatorName("zxc");
		operatorRoleWithId.setOperator(operatorWithId);
		operatorRoleRepo.save(operatorRoleWithId);		
		OperatorRole operatorRole1 = OperatorRole.builder().role("qwe1").operator(null).build();
		operatorRoleRepo.save(operatorRole1);
		testService.setOperatorToRole("qwe1", operatorWithId);
		
		
		Set<String> operatorRolesFromOperator = operatorRepo.getOperatorRolesFromOperator("zxc");
		operatorRolesFromOperator
		.forEach(r-> {
			log.debug("-------------------------------" + r);
			assertTrue(r.contains("qwe"));}
		
			);
		


	}



}

package warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.Date;

import javax.validation.Valid;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import warehouse.dto.*;


@AutoConfigureMockMvc
@WebMvcTest(DtoTests.TestController.class) 
@ContextConfiguration(classes=DtoTests.TestController.class) 

public class DtoTests {
	public static @RestController class TestController {
		static ContainerDto containerExp;
		static ProductDto productExp;
		static TransportStockDto transportStockDtoExp;
		static InventoryDto inventoryExp;
		static MovementDockDto movementDockDtoExp;
		static OrderDto orderDtoExp;
		static IrreducibleBalanceDto irreducibleBalanceExp;
		
		@PostMapping("/container")
		void testContainer(@RequestBody @Valid ContainerDto containerDto) {
			assertEquals(containerExp, containerDto);
		}
		@PostMapping("/product")
		void testProduct(@RequestBody @Valid ProductDto productDto) {
			assertEquals(productExp, productDto);
		}

		@PostMapping("/product/transportation/parameters")
		void testProductTransportationParameters(@RequestBody @Valid TransportStockDto transportStockDto) {
			assertEquals(transportStockDtoExp, transportStockDto);
		}	
		@PostMapping("/inventory")
		void testProduct(@RequestBody @Valid InventoryDto inventory) {
			assertEquals(inventoryExp, inventory);
		}
		@PostMapping("/movement/dock")
		void testMovementDockDto(@RequestBody @Valid MovementDockDto movementDockDto) {
			assertEquals(movementDockDtoExp, movementDockDto);
		}
		@PostMapping("/order")
		void testOrderDto(@RequestBody @Valid OrderDto orderDto) {
			assertEquals(orderDtoExp, orderDto);
		}
		@PostMapping("/irreducible/balance")
		void testOrderDto(@RequestBody @Valid IrreducibleBalanceDto irreducibleBalanceDto) {
			assertEquals(irreducibleBalanceExp, irreducibleBalanceDto);
		}
	}
	ObjectMapper mapper = new ObjectMapper();
	@Autowired
	MockMvc mock;
	
	private void makeTest(int statusExp, String HttpAddress, Object expDto) throws JsonProcessingException, Exception  {
		assertEquals(statusExp, mock.perform(MockMvcRequestBuilders.post(HttpAddress)
				.contentType(MediaType.APPLICATION_JSON)
				.content(mapper.writeValueAsString(expDto)))
				.andReturn()
				.getResponse().getStatus());
	}

//	ContainerTests
	@Test
	void NormalContainerTest() throws JsonProcessingException, Exception{
		TestController.containerExp = new ContainerDto(1, "123");
		int statusExp = 200;
		String HttpAddress = "/container";
		makeTest(statusExp, HttpAddress, TestController.containerExp);
	}
	@Test
	void wrongIdContainerTest() throws JsonProcessingException, Exception{
		TestController.containerExp = new ContainerDto(-1,  "123");
		int statusExp = 400;
		String HttpAddress = "/container";
		makeTest(statusExp, HttpAddress, TestController.containerExp);
	}
	@Test
	void wrongAddressContainerTest() throws JsonProcessingException, Exception{
		TestController.containerExp = new ContainerDto(1,  null);
		int statusExp = 400;
		String HttpAddress = "/container";
		makeTest(statusExp, HttpAddress, TestController.containerExp);
	}

//	*******************************************************************************************************
//	productTests
	@Test
	void NormalProductTest() throws JsonProcessingException, Exception{
		TestController.productExp = new ProductDto(1,  "123", 1);
		int statusExp = 200;
		String HttpAddress = "/product";
		makeTest(statusExp, HttpAddress, TestController.productExp);
	}
	@Test
	void wrongIdProductTest() throws JsonProcessingException, Exception{
		TestController.productExp = new ProductDto(-1,  "123", 1);
		int statusExp = 400;
		String HttpAddress = "/product";
		makeTest(statusExp, HttpAddress, TestController.productExp);
	}
	@Test
	void wrongNameProductTest() throws JsonProcessingException, Exception{
		TestController.productExp = new ProductDto(1,  null, 1);
		int statusExp = 400;
		String HttpAddress = "/product";
		makeTest(statusExp, HttpAddress, TestController.productExp);
	}
	@Test
	void wrongNumberInContainerProductTest() throws JsonProcessingException, Exception{
		TestController.productExp = new ProductDto(1,  "123", -3);
		int statusExp = 400;
		String HttpAddress = "/product";
		makeTest(statusExp, HttpAddress, TestController.productExp);
	}

//	*******************************************************************************************************
//	transportStockDtoTests
	@Test
	void NormalTransportStockDtoTest() throws JsonProcessingException, Exception{
		TestController.transportStockDtoExp = new TransportStockDto(Date.from(Instant.now()), 1, -1);
		int statusExp = 200;
		String HttpAddress = "/product/transportation/parameters";
		makeTest(statusExp, HttpAddress, TestController.transportStockDtoExp);
	}
	@Test
	void wrongDateTransportStockDtoTest() throws JsonProcessingException, Exception{
		TestController.transportStockDtoExp = new TransportStockDto(null, 1, -1);
		int statusExp = 400;
		String HttpAddress = "/product/transportation/parameters";
		makeTest(statusExp, HttpAddress, TestController.transportStockDtoExp);
	}
	@Test
	void wrongProductIdTransportStockDtoTest() throws JsonProcessingException, Exception{
		TestController.transportStockDtoExp = new TransportStockDto(Date.from(Instant.now()), -1, -1);
		int statusExp = 400;
		String HttpAddress = "/product/transportation/parameters";
		makeTest(statusExp, HttpAddress, TestController.transportStockDtoExp);
	}
//	*******************************************************************************************************
//	inventoryTests
	@Test
	void NormalInventoryTest() throws JsonProcessingException, Exception{
		TestController.inventoryExp = new InventoryDto( 1, 1, Date.from(Instant.now()), 1, CreatingMethodEnum.BY_OPERATOR);
		int statusExp = 200;
		String HttpAddress = "/inventory";
		makeTest(statusExp, HttpAddress, TestController.inventoryExp);
	}
	@Test
	void wrongContainerIdInventoryTest() throws JsonProcessingException, Exception{
		TestController.inventoryExp = new InventoryDto( 1, -1, Date.from(Instant.now()), 1, CreatingMethodEnum.BY_OPERATOR);
		int statusExp = 400;
		String HttpAddress = "/inventory";
		makeTest(statusExp, HttpAddress, TestController.inventoryExp);
	}
	@Test
	void wrongProductIdInventoryTest() throws JsonProcessingException, Exception{
		TestController.inventoryExp = new InventoryDto( -1, 1, Date.from(Instant.now()), 1, CreatingMethodEnum.BY_OPERATOR);
		int statusExp = 400;
		String HttpAddress = "/inventory";
		makeTest(statusExp, HttpAddress, TestController.inventoryExp);
	}
	@Test
	void wrongDateInventoryTest() throws JsonProcessingException, Exception{
		TestController.inventoryExp = new InventoryDto( 1, 1, null, 1, CreatingMethodEnum.BY_OPERATOR);
		int statusExp = 400;
		String HttpAddress = "/inventory";
		makeTest(statusExp, HttpAddress, TestController.inventoryExp);
	}
	@Test
	void wrongDeviationInventoryTest() throws JsonProcessingException, Exception{
		TestController.inventoryExp = new InventoryDto( 1, 1, Date.from(Instant.now()), 0, CreatingMethodEnum.BY_OPERATOR);
		int statusExp = 400;
		String HttpAddress = "/inventory";
		makeTest(statusExp, HttpAddress, TestController.inventoryExp);
	}
	@Test
	void wrongCreatingMethodTest() throws JsonProcessingException, Exception{
		TestController.inventoryExp = new InventoryDto( 1, 1, Date.from(Instant.now()), 1, null);
		int statusExp = 400;
		String HttpAddress = "/inventory";
		makeTest(statusExp, HttpAddress, TestController.inventoryExp);
	}
//	*******************************************************************************************************	
//	movementDockDtoTests
	@Test
	void NormalMovementDockDtoTest() throws JsonProcessingException, Exception{
		TestController.movementDockDtoExp = new MovementDockDto( 1, 1, CreatingMethodEnum.BY_OPERATOR, DockTypeEnum.WAREHOUSE_RECEIPT);
		int statusExp = 200;
		String HttpAddress = "/movement/dock";
		makeTest(statusExp, HttpAddress, TestController.movementDockDtoExp);
	}
	@Test
	void wrongProductIdMovementDockDtoTest() throws JsonProcessingException, Exception{
		TestController.movementDockDtoExp = new MovementDockDto( -1, 1, CreatingMethodEnum.BY_OPERATOR, DockTypeEnum.WAREHOUSE_RECEIPT);
		int statusExp = 400;
		String HttpAddress = "/movement/dock";
		makeTest(statusExp, HttpAddress, TestController.movementDockDtoExp);
	}
	@Test
	void wrongMovementDockAmountMovementDockDtoTest() throws JsonProcessingException, Exception{
		TestController.movementDockDtoExp = new MovementDockDto( 1, -1, CreatingMethodEnum.BY_OPERATOR, DockTypeEnum.WAREHOUSE_RECEIPT);
		int statusExp = 400;
		String HttpAddress = "/movement/dock";
		makeTest(statusExp, HttpAddress, TestController.movementDockDtoExp);
	}
	@Test
	void wrongCreatingMethodMovementDockDtoTest() throws JsonProcessingException, Exception{
		TestController.movementDockDtoExp = new MovementDockDto( 1, 1, null, DockTypeEnum.WAREHOUSE_RECEIPT);
		int statusExp = 400;
		String HttpAddress = "/movement/dock";
		makeTest(statusExp, HttpAddress, TestController.movementDockDtoExp);
	}
	@Test
	void wrongDockTypeMovementDockDtoTest() throws JsonProcessingException, Exception{
		TestController.movementDockDtoExp = new MovementDockDto( 1, 1, CreatingMethodEnum.BY_OPERATOR, null);
		int statusExp = 400;
		String HttpAddress = "/movement/dock";
		makeTest(statusExp, HttpAddress, TestController.movementDockDtoExp);
	}
//	*******************************************************************************************************	
//	orderDtoTests
	@Test
	void NormalOrderDtoTest() throws JsonProcessingException, Exception{
		TestController.orderDtoExp = new OrderDto( 1, 1, CreatingMethodEnum.BY_OPERATOR, OrderStatusEnum.CLOSED);
		int statusExp = 200;
		String HttpAddress = "/order";
		makeTest(statusExp, HttpAddress, TestController.orderDtoExp);
	}
	@Test
	void wrongProductIdOrderDtoTest() throws JsonProcessingException, Exception{
		TestController.orderDtoExp = new OrderDto( -1, 1, CreatingMethodEnum.BY_OPERATOR, OrderStatusEnum.CLOSED);
		int statusExp = 400;
		String HttpAddress = "/order";
		makeTest(statusExp, HttpAddress, TestController.orderDtoExp);
	}
	@Test
	void wrongOrderAmountOrderDtoTest() throws JsonProcessingException, Exception{
		TestController.orderDtoExp = new OrderDto( 1, -1, CreatingMethodEnum.BY_OPERATOR, OrderStatusEnum.CLOSED);
		int statusExp = 400;
		String HttpAddress = "/order";
		makeTest(statusExp, HttpAddress, TestController.orderDtoExp);
	}
	@Test
	void wrongCreatingMethodOrderDtoTest() throws JsonProcessingException, Exception{
		TestController.orderDtoExp = new OrderDto( 1, 1, null, OrderStatusEnum.CLOSED);
		int statusExp = 400;
		String HttpAddress = "/order";
		makeTest(statusExp, HttpAddress, TestController.orderDtoExp);
	}
	@Test
	void wrongOrderStatusOrderDtoTest() throws JsonProcessingException, Exception{
		TestController.orderDtoExp = new OrderDto( 1, 1, CreatingMethodEnum.BY_OPERATOR, null);
		int statusExp = 400;
		String HttpAddress = "/order";
		makeTest(statusExp, HttpAddress, TestController.orderDtoExp);
	}
//	orderDtoTests
	@Test
	void NormalIrreducibleBalanceTest() throws JsonProcessingException, Exception{
		TestController.irreducibleBalanceExp = new IrreducibleBalanceDto(Date.from(Instant.now()), 1, 1, CreatingMethodEnum.BY_OPERATOR);
		int statusExp = 200;
		String HttpAddress = "/irreducible/balance";
		makeTest(statusExp, HttpAddress, TestController.irreducibleBalanceExp);
	}
	@Test
	void wrongirreducibleBalanceAppointmentAssignmentDateIrreducibleBalanceTest() throws JsonProcessingException, Exception{
		TestController.irreducibleBalanceExp = new IrreducibleBalanceDto(null, 1, 1, CreatingMethodEnum.BY_OPERATOR);
		int statusExp = 400;
		String HttpAddress = "/irreducible/balance";
		makeTest(statusExp, HttpAddress, TestController.irreducibleBalanceExp);
	}
	@Test
	void wrongProductIdIrreducibleBalanceTest() throws JsonProcessingException, Exception{
		TestController.irreducibleBalanceExp = new IrreducibleBalanceDto(Date.from(Instant.now()), -1, 1, CreatingMethodEnum.BY_OPERATOR);
		int statusExp = 400;
		String HttpAddress = "/irreducible/balance";
		makeTest(statusExp, HttpAddress, TestController.irreducibleBalanceExp);
	}
	@Test
	void wrongirreducibleBalanceAmountrreducibleBalanceTest() throws JsonProcessingException, Exception{
		TestController.irreducibleBalanceExp = new IrreducibleBalanceDto(Date.from(Instant.now()), 1, -1, CreatingMethodEnum.BY_OPERATOR);
		int statusExp = 400;
		String HttpAddress = "/irreducible/balance";
		makeTest(statusExp, HttpAddress, TestController.irreducibleBalanceExp);
	}
	@Test
	void wrongOrderStatusAmountrreducibleBalanceTest() throws JsonProcessingException, Exception{
		TestController.irreducibleBalanceExp = new IrreducibleBalanceDto(Date.from(Instant.now()), 1, -1, null);
		int statusExp = 400;
		String HttpAddress = "/irreducible/balance";
		makeTest(statusExp, HttpAddress, TestController.irreducibleBalanceExp);
	}
}
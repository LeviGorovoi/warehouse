package warehouse;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Instant;
import java.util.Date;

import javax.validation.Valid;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import warehouse.dto.InventoryDto;
import warehouse.dto.MovementDocDto;
import warehouse.dto.OrderDto;
import warehouse.dto.container.CreatingContainerDto;
import warehouse.dto.enums.OrderStatusEnum;
import warehouse.dto.enums.TypeOfDocEnum;
import warehouse.dto.product.CreatingProductDto;
import warehouse.dto.product.IrreducibleBalanceSettingDto;
import warehouse.dto.product.TransportSupplySettingDto;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(DtoTests.TestController.class) 
@ContextConfiguration(classes=DtoTests.TestController.class) 

public class DtoTests {
	
	public static @RestController class TestController {
		static CreatingContainerDto containerExp;
		static CreatingProductDto productExp;
		static TransportSupplySettingDto transportSupplyDtoExp;
		static InventoryDto inventoryExp;
		static MovementDocDto movementDocDtoExp;
		static OrderDto orderDtoExp;
		static IrreducibleBalanceSettingDto irreducibleBalanceExp;
		
		@PostMapping("/container")
		void testContainer(@RequestBody @Valid CreatingContainerDto creatingContainerDto) {
			assertEquals(containerExp, creatingContainerDto);
		}
		@PostMapping("/product")
		void testProduct(@RequestBody @Valid CreatingProductDto creatingProductDto) {
			assertEquals(productExp, creatingProductDto);
		}

		@PostMapping("/product/transportation/parameters")
		void testProductTransportationParameters(@RequestBody @Valid TransportSupplySettingDto transportSupplySettingDto) {
			assertEquals(transportSupplyDtoExp, transportSupplySettingDto);
		}	
		@PostMapping("/inventory")
		void testProduct(@RequestBody @Valid InventoryDto inventory) {
			assertEquals(inventoryExp, inventory);
		}
		@PostMapping("/movement/doc")
		void testMovementDocDto(@RequestBody @Valid MovementDocDto movementDocDto) {
			assertEquals(movementDocDtoExp, movementDocDto);
		}
		@PostMapping("/order")
		void testOrderDto(@RequestBody @Valid OrderDto orderDto) {
			assertEquals(orderDtoExp, orderDto);
		}
		@PostMapping("/irreducible/balance")
		void testOrderDto(@RequestBody @Valid IrreducibleBalanceSettingDto irreducibleBalanceSettingDto) {
			assertEquals(irreducibleBalanceExp, irreducibleBalanceSettingDto);
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
		TestController.containerExp = CreatingContainerDto.builder().address("123").build();
		int statusExp = 200;
		String HttpAddress = "/container";
		makeTest(statusExp, HttpAddress, TestController.containerExp);
	}

	@Test
	void wrongAddressContainerTest() throws JsonProcessingException, Exception{
		TestController.containerExp = CreatingContainerDto.builder().address("").build();
		int statusExp = 400;
		String HttpAddress = "/container";
		makeTest(statusExp, HttpAddress, TestController.containerExp);
	}

//	*******************************************************************************************************
//	productTests
	@Test
	void NormalProductTest() throws JsonProcessingException, Exception{
		TestController.productExp = CreatingProductDto.builder().productName("123").numberInContainer(1).build();
		int statusExp = 200;
		String HttpAddress = "/product";
		makeTest(statusExp, HttpAddress, TestController.productExp);
	}
	@Test
	void wrongNameProductTest() throws JsonProcessingException, Exception{
		TestController.productExp = CreatingProductDto.builder().productName(null).numberInContainer(1).build();;
		int statusExp = 400;
		String HttpAddress = "/product";
		makeTest(statusExp, HttpAddress, TestController.productExp);
	}
	@Test
	void wrongNumberInContainerProductTest() throws JsonProcessingException, Exception{
		TestController.productExp = CreatingProductDto.builder().productName("123").numberInContainer(-3).build();
		int statusExp = 400;
		String HttpAddress = "/product";
		makeTest(statusExp, HttpAddress, TestController.productExp);
	}

//	*******************************************************************************************************
//	transportSupplyDtoTests
	@Test
	void NormalTransportSupplyDtoTest() throws JsonProcessingException, Exception{
		TestController.transportSupplyDtoExp = new TransportSupplySettingDto(Date.from(Instant.now().plusSeconds(1000)), 1, -1);
		int statusExp = 200;
		String HttpAddress = "/product/transportation/parameters";
		makeTest(statusExp, HttpAddress, TestController.transportSupplyDtoExp);
	}
	@Test
	void wrongDateTransportSupplyDtoTest() throws JsonProcessingException, Exception{
		TestController.transportSupplyDtoExp = new TransportSupplySettingDto(Date.from(Instant.now().minusSeconds(1000)), 1, -1);
		int statusExp = 400;
		String HttpAddress = "/product/transportation/parameters";
		makeTest(statusExp, HttpAddress, TestController.transportSupplyDtoExp);
	}
	@Test
	void wrongProductIdTransportSupplyDtoTest() throws JsonProcessingException, Exception{
		TestController.transportSupplyDtoExp = new TransportSupplySettingDto(Date.from(Instant.now().plusSeconds(1000)), -1, -1);
		int statusExp = 400;
		String HttpAddress = "/product/transportation/parameters";
		makeTest(statusExp, HttpAddress, TestController.transportSupplyDtoExp);
	}
//	*******************************************************************************************************
//	inventoryTests
	@Test
	void NormalInventoryTest() throws JsonProcessingException, Exception{
		TestController.inventoryExp = new InventoryDto( 1, 1, Date.from(Instant.now()), 1);
		int statusExp = 200;
		String HttpAddress = "/inventory";
		makeTest(statusExp, HttpAddress, TestController.inventoryExp);
	}
	@Test
	void wrongContainerIdInventoryTest() throws JsonProcessingException, Exception{
		TestController.inventoryExp = new InventoryDto( 1, -1, Date.from(Instant.now()), 1);
		int statusExp = 400;
		String HttpAddress = "/inventory";
		makeTest(statusExp, HttpAddress, TestController.inventoryExp);
	}
	@Test
	void wrongProductIdInventoryTest() throws JsonProcessingException, Exception{
		TestController.inventoryExp = new InventoryDto( -1, 1, Date.from(Instant.now()), 1);
		int statusExp = 400;
		String HttpAddress = "/inventory";
		makeTest(statusExp, HttpAddress, TestController.inventoryExp);
	}
	@Test
	void wrongDateInventoryTest() throws JsonProcessingException, Exception{
		TestController.inventoryExp = new InventoryDto( 1, 1, null, 1);
		int statusExp = 400;
		String HttpAddress = "/inventory";
		makeTest(statusExp, HttpAddress, TestController.inventoryExp);
	}
	@Test
	void wrongDeviationInventoryTest() throws JsonProcessingException, Exception{
		TestController.inventoryExp = new InventoryDto( 1, 1, Date.from(Instant.now()), 0);
		int statusExp = 400;
		String HttpAddress = "/inventory";
		makeTest(statusExp, HttpAddress, TestController.inventoryExp);
	}

//	*******************************************************************************************************	
//	movementDocDtoTests
	@Test
	void NormalMovementDocDtoTest() throws JsonProcessingException, Exception{
		TestController.movementDocDtoExp = new MovementDocDto( 1, 1, TypeOfDocEnum.WAREHOUSE_INVOICE);
		int statusExp = 200;
		String HttpAddress = "/movement/doc";
		makeTest(statusExp, HttpAddress, TestController.movementDocDtoExp);
	}
	@Test
	void wrongProductIdMovementDocDtoTest() throws JsonProcessingException, Exception{
		TestController.movementDocDtoExp = new MovementDocDto( -1, 1, TypeOfDocEnum.WAREHOUSE_INVOICE);
		int statusExp = 400;
		String HttpAddress = "/movement/doc";
		makeTest(statusExp, HttpAddress, TestController.movementDocDtoExp);
	}
	@Test
	void wrongMovementDocAmountMovementDocDtoTest() throws JsonProcessingException, Exception{
		TestController.movementDocDtoExp = new MovementDocDto( 1, -1, TypeOfDocEnum.WAREHOUSE_INVOICE);
		int statusExp = 400;
		String HttpAddress = "/movement/doc";
		makeTest(statusExp, HttpAddress, TestController.movementDocDtoExp);
	}

	@Test
	void wrongDocTypeMovementDocDtoTest() throws JsonProcessingException, Exception{
		TestController.movementDocDtoExp = new MovementDocDto( 1, 1, null);
		int statusExp = 400;
		String HttpAddress = "/movement/doc";
		makeTest(statusExp, HttpAddress, TestController.movementDocDtoExp);
	}
//	*******************************************************************************************************	
//	orderDtoTests
	@Test
	void NormalOrderDtoTest() throws JsonProcessingException, Exception{
		TestController.orderDtoExp = new OrderDto( 1, 1, OrderStatusEnum.CLOSED);
		int statusExp = 200;
		String HttpAddress = "/order";
		makeTest(statusExp, HttpAddress, TestController.orderDtoExp);
	}
	@Test
	void wrongProductIdOrderDtoTest() throws JsonProcessingException, Exception{
		TestController.orderDtoExp = new OrderDto( -1, 1, OrderStatusEnum.CLOSED);
		int statusExp = 400;
		String HttpAddress = "/order";
		makeTest(statusExp, HttpAddress, TestController.orderDtoExp);
	}
	@Test
	void wrongOrderAmountOrderDtoTest() throws JsonProcessingException, Exception{
		TestController.orderDtoExp = new OrderDto( 1, -1, OrderStatusEnum.CLOSED);
		int statusExp = 400;
		String HttpAddress = "/order";
		makeTest(statusExp, HttpAddress, TestController.orderDtoExp);
	}
	@Test
	void wrongOrderStatusOrderDtoTest() throws JsonProcessingException, Exception{
		TestController.orderDtoExp = new OrderDto( 1, 1, null);
		int statusExp = 400;
		String HttpAddress = "/order";
		makeTest(statusExp, HttpAddress, TestController.orderDtoExp);
	}
//	orderDtoTests
	@Test
	void NormalIrreducibleBalanceTest() throws JsonProcessingException, Exception{
		TestController.irreducibleBalanceExp = new IrreducibleBalanceSettingDto(Date.from(Instant.now().plusSeconds(1000)), 1, 1);
		int statusExp = 200;
		String HttpAddress = "/irreducible/balance";
		makeTest(statusExp, HttpAddress, TestController.irreducibleBalanceExp);
	}
	@Test
	void wrongIrreducibleBalanceSettingDateIrreducibleBalanceTest() throws JsonProcessingException, Exception{
		TestController.irreducibleBalanceExp = new IrreducibleBalanceSettingDto(Date.from(Instant.now().minusSeconds(1000)), 1, 1);
		int statusExp = 400;
		String HttpAddress = "/irreducible/balance";
		makeTest(statusExp, HttpAddress, TestController.irreducibleBalanceExp);
	}
	@Test
	void nullIrreducibleBalanceSettingDateIrreducibleBalanceTest() throws JsonProcessingException, Exception{
		TestController.irreducibleBalanceExp = new IrreducibleBalanceSettingDto(null, 1, 1);
		int statusExp = 400;
		String HttpAddress = "/irreducible/balance";
		makeTest(statusExp, HttpAddress, TestController.irreducibleBalanceExp);
	}
	@Test
	void wrongProductIdIrreducibleBalanceTest() throws JsonProcessingException, Exception{
		TestController.irreducibleBalanceExp = new IrreducibleBalanceSettingDto(Date.from(Instant.now().plusSeconds(1000)), -1, 1);
		int statusExp = 400;
		String HttpAddress = "/irreducible/balance";
		makeTest(statusExp, HttpAddress, TestController.irreducibleBalanceExp);
	}
	@Test
	void wrongirreducibleBalanceAmountrreducibleBalanceTest() throws JsonProcessingException, Exception{
		TestController.irreducibleBalanceExp = new IrreducibleBalanceSettingDto(Date.from(Instant.now().plusSeconds(1000)), 1, -1);
		int statusExp = 400;
		String HttpAddress = "/irreducible/balance";
		makeTest(statusExp, HttpAddress, TestController.irreducibleBalanceExp);
	}
	
}

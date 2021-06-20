package warehouse.doc;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;
import warehouse.dto.enums.*;

@Document(collection = "orders")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Builder
public class Order {
	@Id
	private ObjectId orderId = null;
	private LocalDateTime orderOpeningDate;
	private LocalDateTime orderClosingDate;
	private long productId;
	private int orderAmount;
	private OrderStatusEnum orderStatus;
	private CreatingMethodEnum creatingMethod;
	private int operatorId;
}

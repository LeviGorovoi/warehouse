package warehouse.doc;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;
import warehouse.dto.*;

@Document(collection = "orders")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Builder
public class Order {
	@Id
	ObjectId orderId = null;
	LocalDateTime orderOpeningDate;
	LocalDateTime orderClosingDate;
	long productId;
	int orderAmount;
	OrderStatusEnum orderStatus;
	CreatingMethodEnum creatingMethod;
	int operatorId;
}

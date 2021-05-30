package warehouse.doc;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;
import warehouse.dto.*;

@Document(collection = "inventories")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Builder
public class Inventory {
	@Id
	ObjectId inventoryId = null;
	LocalDateTime inventoryDate;
	long containerId;
	long productId;
	LocalDateTime receiptDateTime;
	int deviation;
	CreatingMethodEnum creatingMethod;
	int operatorId;
}

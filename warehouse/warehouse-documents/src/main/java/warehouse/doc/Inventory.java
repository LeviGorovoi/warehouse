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
	private ObjectId inventoryId = null;
	private LocalDateTime inventoryDate;
	private long containerId;
	private long productId;
	private LocalDateTime receiptDateTime;
	private int deviation;
	private CreatingMethodEnum creatingMethod;
	private int operatorId;
}

package warehouse.doc;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;
import warehouse.dto.*;

@Document(collection = "movement_docs")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Builder
public class MovementDoc {
	@Id
	private ObjectId movementDocId = null;
	private TypeOfDocEnum docTypeEnum;
	private LocalDateTime movementDocDateTime;
	private long productId;
	private int movementDocAmount;
	private CreatingMethodEnum creatingMethod;
	private int operatorId;
	private ObjectId orderId;

	public MovementDoc (TypeOfDocEnum docTypeEnum, LocalDateTime movementDocDateTime, long productId, int movementDocAmount,
		 CreatingMethodEnum creatingMethod, int operatorId, ObjectId orderId) {
		 this(null, docTypeEnum, movementDocDateTime, productId, movementDocAmount, creatingMethod, operatorId, orderId);
	}
	
}

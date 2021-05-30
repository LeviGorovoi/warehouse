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
public class MovementDock {
	@Id
	ObjectId movementDockId = null;
	LocalDateTime movementDockDateTime;
	long productId;
	int movementDockAmount;
	DockTypeEnum dockType;
	CreatingMethodEnum creatingMethod;
	int operatorId;
	ObjectId orderId;

	public MovementDock (LocalDateTime movementDockDateTime, long productId, int movementDockAmount,
	DockTypeEnum dockType, CreatingMethodEnum creatingMethod, int operatorId, ObjectId orderId) {
		
	}
	
}

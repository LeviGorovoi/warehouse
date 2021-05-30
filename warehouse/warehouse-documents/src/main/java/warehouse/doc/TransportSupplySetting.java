package warehouse.doc;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;
import warehouse.dto.*;

@Document(collection = "transport_supply_setting")
@AllArgsConstructor()
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class TransportSupplySetting {
	@Id
	ObjectId transportSupplySettingId = null;
	LocalDateTime documentDateTime;
	LocalDateTime containerPurposeSettingDate;
	long productId;
	int transportSupply;
	CreatingMethodEnum creatingMethod;
	int operatorId;
}

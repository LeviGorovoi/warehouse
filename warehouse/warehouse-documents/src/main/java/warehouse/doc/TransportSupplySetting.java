package warehouse.doc;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;
import warehouse.dto.enums.*;

@Document(collection = "transport_supply_setting")
@AllArgsConstructor()
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class TransportSupplySetting {
	@Id
	private ObjectId transportSupplySettingId = null;
	private LocalDateTime documentDateTime;
	private LocalDateTime containerPurposeSettingDate;
	private long productId;
	private int transportSupply;
	private CreatingMethodEnum creatingMethod;
	private int operatorId;
}

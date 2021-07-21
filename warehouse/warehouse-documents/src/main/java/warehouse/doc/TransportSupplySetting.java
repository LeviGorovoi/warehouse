package warehouse.doc;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Document(collection = "transport_supply_setting")
@AllArgsConstructor()
@Getter
@Builder
public class TransportSupplySetting {
	@Id
	private ObjectId id = null;
	private LocalDateTime documentDateTime;
	private LocalDateTime SettingDate;
	private long productId;
	private int transportSupply;
	private int operatorId;
}

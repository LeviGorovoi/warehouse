package warehouse.doc.product;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Document(collection = "irreducible_balance_setting")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Setter
@Getter
public class IrreducibleBalanceSettingDoc {
	@EqualsAndHashCode.Exclude
	private ObjectId id = null;
	private LocalDateTime documentDateTime;
	private LocalDateTime settingDate;
	private long productId;
	private int irreducibleBalance;
	private long executorOperatorId;
	
}

package warehouse.doc;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

import warehouse.dto.enums.*;

@Document(collection = "role_setting")
@AllArgsConstructor()
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Builder
public class OperatorToRoleSetting {
	@Id
	private ObjectId OperatorToRoleSettingId = null;
	private LocalDateTime documentDateTime;
	private LocalDateTime roleSettingDate;
	private long operatorId;
	private long operatorRoleId;
	private CreatingMethodEnum creatingMethod;
	private int operatorManagerId;
}

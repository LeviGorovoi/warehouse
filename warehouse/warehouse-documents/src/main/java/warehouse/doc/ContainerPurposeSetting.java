package warehouse.doc;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;
import warehouse.dto.*;
import warehouse.dto.CreatingMethodEnum;

@Document(collection = "container_purpose_setting")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Builder
public class ContainerPurposeSetting {
	@Id
	ObjectId containerPurposeSettingId = null;
	LocalDateTime documentDateTime;
	LocalDateTime containerPurposeSettingDate;
	long containerId;
	long productId;
	CreatingMethodEnum creatingMethod;
	int operatorId;
}

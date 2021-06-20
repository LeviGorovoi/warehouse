package warehouse.doc;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;
import warehouse.dto.enums.*;

@Document(collection = "container_purpose_setting")
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
public class ContainerPurposeSetting {
	@Id
	@EqualsAndHashCode.Exclude
	private ObjectId containerPurposeSettingId = null;
	private LocalDateTime documentDateTime;
	private LocalDateTime containerPurposeSettingDate;
	private long containerId;
	private long productId;
	private CreatingMethodEnum creatingMethod;
	private int operatorId;
}

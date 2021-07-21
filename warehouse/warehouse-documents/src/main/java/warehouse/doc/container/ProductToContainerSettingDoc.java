package warehouse.doc.container;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.*;

@Document(collection = "product_to_container_setting")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Setter
@Getter
public class ProductToContainerSettingDoc {
	@EqualsAndHashCode.Exclude
	private ObjectId id = null;
	private LocalDateTime documentDateTime;
	private LocalDateTime settingDate;
	private long containerId;
	private long productId;
	private long executorOperatorId;

}

package warehouse.doc.container;

import java.time.LocalDateTime;

import javax.validation.constraints.*;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Document(collection = "change_container_address")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Setter
@Getter
public class ChangeContainerAddressDoc {
	@EqualsAndHashCode.Exclude
	private ObjectId id = null;
	private LocalDateTime documentDateTime;
	private long containerId;
	private String newAddress;
	private long executorOperatorId;
}

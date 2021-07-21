package warehouse.doc.container;


import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Document(collection = "creating_container")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Setter
@Getter
public class CreatingContainerDoc {
	@EqualsAndHashCode.Exclude
	private ObjectId id = null;
	private LocalDateTime documentDateTime;
	private String address;
	private long executorOperatorId;
}

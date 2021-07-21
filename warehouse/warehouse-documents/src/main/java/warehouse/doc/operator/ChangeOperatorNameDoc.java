package warehouse.doc.operator;

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
public class ChangeOperatorNameDoc {
	@EqualsAndHashCode.Exclude
	private ObjectId id = null;
	private LocalDateTime documentDateTime;
	private long operatorId;
	private String newOperatorName;
	private long executorOperatorId;
}

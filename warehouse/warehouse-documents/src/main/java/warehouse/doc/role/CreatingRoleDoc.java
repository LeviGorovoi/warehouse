package warehouse.doc.role;


import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Document(collection = "creating_role")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Setter
@Getter
public class CreatingRoleDoc {
	@EqualsAndHashCode.Exclude
	private ObjectId id = null;
	private LocalDateTime documentDateTime;
	private String role;
	private long executorOperatorId;
}

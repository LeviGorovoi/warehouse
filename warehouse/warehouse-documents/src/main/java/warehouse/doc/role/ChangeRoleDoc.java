package warehouse.doc.role;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Document(collection = "change_role")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Setter
@Getter
public class ChangeRoleDoc {
	@EqualsAndHashCode.Exclude
	private ObjectId id = null;
	private LocalDateTime documentDateTime;
	private long roleId;
	private String newRole;
	private long executorOperatorId;
}

package warehouse.doc.role;


import java.time.LocalDateTime;

import org.bson.types.ObjectId;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Setter
@Getter
public class OperatorToRoleSettingDoc {
	@EqualsAndHashCode.Exclude
	private ObjectId id = null;
	private LocalDateTime documentDateTime;
	private LocalDateTime settingDate;
	private long roleId;
	private long operatorId;
	private long executorOperatorId;
	
}

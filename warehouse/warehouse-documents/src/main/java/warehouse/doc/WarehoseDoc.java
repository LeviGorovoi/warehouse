package warehouse.doc;


import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;
import warehouse.dto.ParentDto;

@Document(collection = "warehose_docs")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Setter
@Getter
public class WarehoseDoc {
	@EqualsAndHashCode.Exclude
	private ObjectId id = null;
	@EqualsAndHashCode.Exclude
	private LocalDateTime documentDateTime;
	private String incomingDtoType;
	private ParentDto incomingDto;
}

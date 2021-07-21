package warehouse.doc.product;


import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Document(collection = "creating_product")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Setter
@Getter
public class CreatingProductDoc {
	@EqualsAndHashCode.Exclude
	private ObjectId id = null;
	private LocalDateTime documentDateTime;
	private String productName;
	private int numberInContainer;
	private long executorOperatorId;
}

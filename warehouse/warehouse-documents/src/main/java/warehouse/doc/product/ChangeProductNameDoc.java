package warehouse.doc.product;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;

@Document(collection = "change_product_name")
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Setter
@Getter
public class ChangeProductNameDoc {
	@EqualsAndHashCode.Exclude
	private ObjectId id = null;
	private LocalDateTime documentDateTime;
	private long productId;
	private String newProductName;
	private long executorOperatorId;
}

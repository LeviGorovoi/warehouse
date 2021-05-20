package warehouse.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.*;
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RemainderInContainerId implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private long containerId;
private long productId;
LocalDateTime receiptDate;
}

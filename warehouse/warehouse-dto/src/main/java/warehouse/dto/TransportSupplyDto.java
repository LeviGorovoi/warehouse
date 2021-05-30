package warehouse.dto;

import java.util.Date;

import javax.validation.constraints.*;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class TransportSupplyDto {
	@NotNull
	public Date transportSupplySettingDate;
	@Min(1)
	public long productId;
	public int transportStock; // in days

}

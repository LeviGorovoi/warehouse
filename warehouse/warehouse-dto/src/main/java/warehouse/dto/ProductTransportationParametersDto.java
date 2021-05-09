package warehouse.dto;

import java.util.Date;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class ProductTransportationParametersDto {
	@NotNull
	public Date transportationParametersAssignmentDate;
	@Min(1)
	public long productId;
	public int transportUnit;
	public int transportStock; // in days

}

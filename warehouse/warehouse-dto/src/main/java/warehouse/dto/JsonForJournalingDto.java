package warehouse.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

import lombok.*;

import warehouse.dto.enums.CreatingMethodEnum;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Setter
@Getter
public class JsonForJournalingDto {
	private String className;;
	private String jsonForJournaling;
}

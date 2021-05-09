package warehouse.dto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import lombok.extern.slf4j.Slf4j;
@Slf4j
public class NotEqualsValidator implements ConstraintValidator<NotEquals, Integer>{
	int value;
	String errorMessage;
    @Override
    public void initialize(NotEquals annotation) { 
         value = annotation.value();
         
    }

	@Override
	public boolean isValid(Integer testedValue, ConstraintValidatorContext context) {
		if(testedValue==value) {
			log.debug("must not be equal to {}", value);
			return false;
		}
		return true;
	}

}

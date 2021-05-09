package warehouse.dto;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
@Constraint(validatedBy=NotEqualsValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface NotEquals {	
	public int value() default 0;
	String message() default "{warehouse.dto.NotEquals.message}";
//	"must not be equal to 0";
	Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}

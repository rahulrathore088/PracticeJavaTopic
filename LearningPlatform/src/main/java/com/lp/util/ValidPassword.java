package com.lp.util;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.lp.validator.UserValidator;

@Retention(RUNTIME)
@Target({ FIELD, METHOD, PARAMETER })
@Constraint(validatedBy = UserValidator.class)
public @interface ValidPassword {
	
	public String message() default "Invalid password: must contain special Symbol,number,Captial character";
    public Class<?>[] groups() default {};
    public Class<? extends Payload>[] payload() default {};

}

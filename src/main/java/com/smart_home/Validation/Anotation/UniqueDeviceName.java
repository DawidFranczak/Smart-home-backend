package com.smart_home.Validation.Anotation;

import com.smart_home.Validation.Validator.UniqueDeviceNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueDeviceNameValidator.class)
@Target({ElementType.FIELD, ElementType.ANNOTATION_TYPE,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueDeviceName {
    String message() default "Device name already exist.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

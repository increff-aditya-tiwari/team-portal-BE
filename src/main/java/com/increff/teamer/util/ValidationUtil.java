package com.increff.teamer.util;

import com.increff.teamer.exception.CommonApiException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class ValidationUtil {

    private static final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    public ValidationUtil(){

    }

    public <T> void validateForm(T form) throws CommonApiException {
        Set<ConstraintViolation<T>> violations = factory.getValidator().validate(form);
        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<T> violation : violations) {
                errorMessages.append(violation.getPropertyPath()).append(" ").append(violation.getMessage()).append("; ");
            }
            throw new CommonApiException(HttpStatus.BAD_REQUEST, errorMessages.toString().trim());
        }
    }
}

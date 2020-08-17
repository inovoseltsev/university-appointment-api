package com.novoseltsev.appointmentapi.validation.annotation.impl;

import com.novoseltsev.appointmentapi.validation.annotation.TeacherTime;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TeacherTimeValidator implements ConstraintValidator<TeacherTime, Integer> {

    @Value("${minimum.appointed.time}")
    private Integer minimalTime;

    @Override
    public void initialize(TeacherTime constraintAnnotation) {
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        return value.compareTo(minimalTime) >= 0
                && value % minimalTime == 0
                && value.compareTo(200) < 0;
    }
}

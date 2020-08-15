package com.novoseltsev.appointmentapi.exception.util;

import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.domain.role.UserRole;
import com.novoseltsev.appointmentapi.exception.user.InappropriateUserRoleException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

public final class ExceptionUtil {

    public static void checkArgumentForNull(Object obj, String processName) {
        String errorMessage = "Cannot " + processName
                + " because argument is null!";
        if (obj == null) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void checkAppointmentUsersForRoleMatching(User teacher,
                                                            User student) {
        UserRole teacherRole = teacher.getRole();
        UserRole studentRole = student.getRole();
        if (!teacherRole.equals(UserRole.TEACHER)) {
            throw new InappropriateUserRoleException("Teacher argument has "
                    + "role \"" + teacherRole + "\"!");
        } else if (!studentRole.equals(UserRole.STUDENT)) {
            throw new InappropriateUserRoleException("Student argument has "
                    + "role \"" + teacherRole + "\"!");
        }
    }

    public static Map<String, String> handleValidationErrors(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

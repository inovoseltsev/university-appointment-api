package com.novoseltsev.appointmentapi.exception.util;

import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.domain.role.UserRole;
import com.novoseltsev.appointmentapi.exception.InappropriateUserRoleException;

public final class ExceptionUtil {

    public static void checkArgumentForNull(Object obj,
                                            String processName) {
        String errorMessage = "User cannot be " + processName
                + " because argument is null!";
        if (obj == null) {
            throw new IllegalArgumentException(errorMessage);
        }
    }

    public static void checkForRoleMatching(User user, UserRole forbiddenRole) {
        if (user.getRole().equals(forbiddenRole)) {
            throw new InappropriateUserRoleException("User role is "
                    + "inappropriate!");
        }
    }
}

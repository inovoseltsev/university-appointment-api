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
}

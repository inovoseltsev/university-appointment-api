package com.novoseltsev.appointmentapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.appointmentapi.domain.entity.Appointment;
import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.domain.role.UserRole;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppointmentDto {

    @Positive
    @NotNull
    Long id;

    @NotBlank
    private String startDate;

    @NotBlank
    private String endDate;

    @Positive
    @NotNull
    private Long studentId;

    @Positive
    @NotNull
    private Long teacherId;

    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("dd-MM-yyyy k:mm");

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public Appointment toAppointment() {
        Appointment appointment = new Appointment();
        appointment.setId(id);
        LocalDateTime start = LocalDateTime.parse(startDate, FORMATTER);
        LocalDateTime end = LocalDateTime.parse(endDate, FORMATTER);
        appointment.setStartTime(Timestamp.valueOf(start));
        appointment.setEndTime(Timestamp.valueOf(end));

        User student = new User();
        student.setId(studentId);
        student.setRole(UserRole.STUDENT);

        User teacher = new User();
        teacher.setId(teacherId);
        teacher.setRole(UserRole.TEACHER);

        appointment.setUsers(student, teacher);
        return appointment;
    }

    public static AppointmentDto from(Appointment appointment) {
        AppointmentDto appointmentDto = new AppointmentDto();
        appointmentDto.setId(appointment.getId());
        appointmentDto.setStartDate(DATE_FORMAT.format(appointment.getStartTime()));
        appointmentDto.setEndDate(DATE_FORMAT.format(appointment.getEndTime()));

        List<User> users = new ArrayList<>(appointment.getUsers());
        appointmentDto.setStudentId(users.get(0).getId());
        appointmentDto.setTeacherId(users.get(1).getId());

        return appointmentDto;
    }
}

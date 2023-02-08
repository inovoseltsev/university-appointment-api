package com.novoseltsev.appointmentapi.service.impl;

import com.novoseltsev.appointmentapi.domain.entity.Appointment;
import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.domain.entity.UuidUserInfo;
import com.novoseltsev.appointmentapi.domain.role.UserRole;
import com.novoseltsev.appointmentapi.domain.status.AppointmentStatus;
import com.novoseltsev.appointmentapi.exception.time.AppointmentNotFoundException;
import com.novoseltsev.appointmentapi.exception.time.IncorrectTimeIntervalException;
import com.novoseltsev.appointmentapi.exception.time.StartEndTimeException;
import com.novoseltsev.appointmentapi.repository.AppointmentRepository;
import com.novoseltsev.appointmentapi.service.AppointmentService;
import com.novoseltsev.appointmentapi.service.MailSenderService;
import com.novoseltsev.appointmentapi.service.UserService;
import com.novoseltsev.appointmentapi.service.UuidUserInfoService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import static com.novoseltsev.appointmentapi.exception.util.ExceptionUtil.checkAppointmentUsersForRoleMatching;

@Component
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private UuidUserInfoService uuidUserInfoService;

    @Autowired
    private MailSenderService mailSenderService;

    @Override
    @Transactional
    public Appointment create(Appointment appointment) {
        User student = getUserByRoleFromAppointment(appointment, UserRole.STUDENT);
        User teacher = getUserByRoleFromAppointment(appointment, UserRole.TEACHER);
        checkAppointmentValidity(appointment, student, teacher);
        student.addAppointment(appointment);
        teacher.addAppointment(appointment);
        Appointment savedAppointment = appointmentRepository.save(appointment);
        sendAppointmentActionsMessages(student, teacher, savedAppointment);
        return savedAppointment;
    }

    @Override
    @Transactional
    public Appointment update(Appointment updatedAppointment) {
        User student = getUserByRoleFromAppointment(updatedAppointment,
                UserRole.STUDENT);
        User teacher = getUserByRoleFromAppointment(updatedAppointment,
                UserRole.TEACHER);
        checkAppointmentValidity(updatedAppointment, student, teacher);
        Appointment savedAppointment = findById(updatedAppointment.getId());
        savedAppointment.setStartTime(updatedAppointment.getStartTime());
        savedAppointment.setEndTime(updatedAppointment.getEndTime());
        return appointmentRepository.save(savedAppointment);
    }

    @Override
    @Transactional
    public void changeAppointmentStatus(Long appointmentId, AppointmentStatus status) {
        Appointment appointment = findById(appointmentId);
        appointment.setStatus(status);
        appointmentRepository.save(appointment);
    }

    @Override
    @Transactional
    public void changeAppointmentStatusByCode(Long appointmentId, AppointmentStatus status, String code) {
        UuidUserInfo uuidUserInfo = uuidUserInfoService.findByUuid(code);
        if (uuidUserInfo != null) {
            changeAppointmentStatus(appointmentId, status);
            uuidUserInfoService.delete(uuidUserInfo);
        }
    }

    @Override
    @Transactional
    public void markAppointmentAsDeleted(Long appointmentId) {
        changeAppointmentStatus(appointmentId, AppointmentStatus.DELETED);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Appointment findById(Long id) {
        return appointmentRepository.findById(id).orElseThrow(AppointmentNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Appointment> findUserAppointments(Long userId) {
        Set<Appointment> userAppointments = userService.findById(userId).getAppointments();
        return userAppointments.stream()
                .filter(appointment -> !appointment.getStatus().equals(AppointmentStatus.DELETED))
                .collect(Collectors.toSet());
    }

    private void checkAppointmentValidity(Appointment newAppointment, User student, User teacher) {
        checkAppointmentTimeValidity(newAppointment);
        checkAppointmentUsersForRoleMatching(student, teacher);
        checkUserTimeIntervalIntersection(student, newAppointment);
        checkUserTimeIntervalIntersection(teacher, newAppointment);
    }

    private void checkUserTimeIntervalIntersection(User user, Appointment appointment) {
        Set<Appointment> userAppointments = user.getAppointments();
        if (!userAppointments.isEmpty()) {
            userAppointments.forEach(userAppointment -> {
                if (!userAppointment.getId().equals(appointment.getId()) && !userAppointment.getStatus().equals(AppointmentStatus.DELETED)) {
                    Date startTime = userAppointment.getStartTime();
                    Date endTime = userAppointment.getEndTime();
                    if (appointment.getStartTime().before(endTime) && appointment.getEndTime().after(startTime)) {
                        throw new IncorrectTimeIntervalException("Appointment time intersects with existed!");
                    }
                }
            });
        }
    }

    private void checkAppointmentTimeValidity(Appointment appointment) {
        if (appointment.getStartTime().after(appointment.getEndTime())) {
            throw new StartEndTimeException("End date less than start date");
        }
    }

    private User getUserByRoleFromAppointment(Appointment appointment, UserRole role) {
        User savedUser = null;
        for (User user : appointment.getUsers()) {
            if (user.getRole().equals(role)) {
                savedUser = userService.findById(user.getId());
                break;
            }
        }
        return savedUser;
    }

    private void sendAppointmentActionsMessages(User student, User teacher, Appointment appointment) {
        UuidUserInfo reservationUuid = uuidUserInfoService.create(student.getId());
        UuidUserInfo approveUuid = uuidUserInfoService.create(teacher.getId());
        UuidUserInfo declineUuid = uuidUserInfoService.create(teacher.getId());

        String studentMessage = getStudentMessage(reservationUuid.getUuid(), appointment.getId());

        String teacherMessage = getTeacherMessage(approveUuid.getUuid(), declineUuid.getUuid(), student, appointment);

        mailSenderService.sendMessage(student.getEmail(), "Reservation " + "actions", studentMessage);
        mailSenderService.sendMessage(teacher.getEmail(), "Appointment " + "actions", teacherMessage);
    }

    private String getStudentMessage(String uuid, Long appointmentId) {
        return String.format(
                "You have just reserved an appointment, to cancel reservation"
                        + "visit this link:" + System.lineSeparator()
                        + "http://localhost:8080/api/v1/appointments-api/"
                        + "appointments/cancel-reservation/code/%s/%s", uuid, appointmentId
        );
    }

    private String getTeacherMessage(String approveUuid, String declineUuid, User student, Appointment appointment) {
        SimpleDateFormat dayMonthFormat = new SimpleDateFormat("dd.MM");
        SimpleDateFormat hoursFormat = new SimpleDateFormat("HH:mm");
        String appointmentDay = dayMonthFormat.format(appointment.getStartTime());
        String appointmentStart = hoursFormat.format(appointment.getStartTime());
        String appointmentEnd = hoursFormat.format(appointment.getEndTime());
        String teacherMessage = "Student %s %s, has just created appointment "
            + "with you %s at %s - %s. To approve meeting visit "
            + "this link:" + System.lineSeparator()
            + "http://localhost:8080/api/v1/appointments-api/"
            + "appointments/confirmation/code/%s/%s"
            + System.lineSeparator()
            + "To decline visit this:" + System.lineSeparator()
            + "http://localhost:8080/api/v1/appointments-api/"
            + "appointments/revocation/code/%s/%s";
        return String.format(
            teacherMessage,
            student.getFirstName(),
            student.getLastName(),
            appointmentDay,
            appointmentStart,
            appointmentEnd,
            approveUuid,
            appointment.getId(),
            declineUuid,
            appointment.getId()
        );
    }
}

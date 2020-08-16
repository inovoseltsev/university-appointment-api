package com.novoseltsev.appointmentapi.service.impl;

import com.novoseltsev.appointmentapi.domain.entity.Appointment;
import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.domain.role.UserRole;
import com.novoseltsev.appointmentapi.domain.status.AppointmentStatus;
import com.novoseltsev.appointmentapi.exception.time.AppointmentNotFoundException;
import com.novoseltsev.appointmentapi.exception.time.IncorrectTimeIntervalException;
import com.novoseltsev.appointmentapi.exception.time.StartEndTimeException;
import com.novoseltsev.appointmentapi.repository.AppointmentRepository;
import com.novoseltsev.appointmentapi.service.AppointmentService;
import com.novoseltsev.appointmentapi.service.UserService;
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

    @Override
    @Transactional
    public Appointment create(Appointment appointment) {
        User student = getUserByRoleFromAppointment(appointment,
                UserRole.STUDENT);
        User teacher = getUserByRoleFromAppointment(appointment,
                UserRole.TEACHER);
        checkAppointmentValidity(appointment, student, teacher);
        student.addAppointment(appointment);
        teacher.addAppointment(appointment);
        return appointmentRepository.save(appointment);
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
    public void approveAppointment(Long appointmentId) {
        Appointment appointment = findById(appointmentId);
        appointment.setStatus(AppointmentStatus.APPROVED);
        appointmentRepository.save(appointment);
    }

    @Override
    public void declineAppointment(Long appointmentId) {
        Appointment appointment = findById(appointmentId);
        appointment.setStatus(AppointmentStatus.DECLINED);
        appointmentRepository.save(appointment);
    }

    @Override
    public void markAppointmentAsDeleted(Long appointmentId) {
        Appointment appointment = findById(appointmentId);
        appointment.setStatus(AppointmentStatus.DELETED);
        appointmentRepository.save(appointment);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Appointment findById(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(AppointmentNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Appointment> findUserAppointments(Long userId) {
        Set<Appointment> userAppointments =
                userService.findById(userId).getAppointments();
        return userAppointments.stream()
                .filter(appointment -> !appointment.getStatus()
                        .equals(AppointmentStatus.DELETED))
                .collect(Collectors.toSet());
    }

    private void checkAppointmentValidity(
            Appointment newAppointment,
            User student, User teacher
    ) {
        checkAppointmentTimeValidity(newAppointment);
        checkAppointmentUsersForRoleMatching(student, teacher);
        checkUserTimeIntervalIntersection(student, newAppointment);
        checkUserTimeIntervalIntersection(teacher, newAppointment);
    }

    private void checkUserTimeIntervalIntersection(
            User user,
            Appointment appointment
    ) {
        Set<Appointment> userAppointments = user.getAppointments();
        if (!userAppointments.isEmpty()) {
            userAppointments.forEach(userAppointment -> {
                if (!userAppointment.getId().equals(appointment.getId())
                        && !userAppointment.getStatus()
                        .equals(AppointmentStatus.DELETED)) {
                    Date startTime = userAppointment.getStartTime();
                    Date endTime = userAppointment.getEndTime();
                    if (appointment.getStartTime().before(endTime)
                            && appointment.getEndTime().after(startTime)) {
                        throw new IncorrectTimeIntervalException(
                                "Appointment time intersects with existed!");
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

    private User getUserByRoleFromAppointment(Appointment appointment,
                                              UserRole role) {
        User savedUser = null;
        for (User user : appointment.getUsers()) {
            if (user.getRole().equals(role)) {
                savedUser = userService.findById(user.getId());
                break;
            }
        }
        return savedUser;
    }
}

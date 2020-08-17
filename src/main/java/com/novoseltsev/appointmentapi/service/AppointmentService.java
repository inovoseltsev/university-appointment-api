package com.novoseltsev.appointmentapi.service;

import com.novoseltsev.appointmentapi.domain.entity.Appointment;
import com.novoseltsev.appointmentapi.domain.status.AppointmentStatus;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public interface AppointmentService {

    Appointment create(Appointment updatedAppointment);

    Appointment update(Appointment updatedAppointment);

    void changeAppointmentStatus(Long appointmentId, AppointmentStatus status);

    void changeAppointmentStatusByCode(Long appointmentId,
                                   AppointmentStatus status, String code);

    void markAppointmentAsDeleted(Long appointmentId);

    void deleteById(Long id);

    Appointment findById(Long id);

    Set<Appointment> findUserAppointments(Long userId);
}

package com.novoseltsev.appointmentapi.service;

import com.novoseltsev.appointmentapi.domain.entity.Appointment;
import java.util.Set;
import org.springframework.stereotype.Service;

@Service
public interface AppointmentService {

    Appointment create(Appointment updatedAppointment);

    Appointment update(Appointment updatedAppointment);

    void approveAppointment(Long appointmentId);

    void declineAppointment(Long appointmentId);

    void markAppointmentAsDeleted(Long appointmentId);

    void deleteById(Long id);

    Appointment findById(Long id);

    Set<Appointment> findUserAppointments(Long userId);
}

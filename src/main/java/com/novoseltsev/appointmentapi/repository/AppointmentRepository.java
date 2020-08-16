package com.novoseltsev.appointmentapi.repository;

import com.novoseltsev.appointmentapi.domain.entity.Appointment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment,
        Long> {
}

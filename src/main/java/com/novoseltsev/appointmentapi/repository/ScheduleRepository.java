package com.novoseltsev.appointmentapi.repository;

import com.novoseltsev.appointmentapi.domain.entity.ScheduleDay;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ScheduleRepository extends CrudRepository<ScheduleDay, Long> {
}

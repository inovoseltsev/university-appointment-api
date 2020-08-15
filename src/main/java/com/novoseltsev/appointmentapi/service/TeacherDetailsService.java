package com.novoseltsev.appointmentapi.service;

import com.novoseltsev.appointmentapi.domain.entity.Price;
import com.novoseltsev.appointmentapi.domain.entity.ScheduleDay;
import org.springframework.stereotype.Service;

@Service
public interface TeacherDetailsService {

    void addPriceToPriceList(Price price, Long teacherId);

    void addDayToSchedule(ScheduleDay day, Long teacherId);
}

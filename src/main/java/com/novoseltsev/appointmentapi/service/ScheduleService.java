package com.novoseltsev.appointmentapi.service;

import com.novoseltsev.appointmentapi.domain.entity.ScheduleDay;
import java.util.List;
import java.util.Queue;
import org.springframework.stereotype.Service;

@Service
public interface ScheduleService {

    ScheduleDay createDay(ScheduleDay scheduleDay, Long teacherId);

    void createAll(List<ScheduleDay> days, Long teacherId);

    ScheduleDay updateDay(ScheduleDay scheduleDay);

    void updateAllTeacherDays(Queue<ScheduleDay> updatedDays, Long teacherId);

    void deleteDayById(Long id);

    void deleteAll(List<Long> scheduleDayIdList);

    ScheduleDay findById(Long id);

    List<ScheduleDay> findAllByIdList(List<Long> scheduleDayIdList);

    List<ScheduleDay> findTeacherScheduleDays(Long teacherId);
}

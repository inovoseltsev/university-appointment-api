package com.novoseltsev.appointmentapi.service.impl;

import com.novoseltsev.appointmentapi.domain.entity.ScheduleDay;
import com.novoseltsev.appointmentapi.domain.entity.TeacherDetails;
import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.exception.schedule.DayNotFoundException;
import com.novoseltsev.appointmentapi.exception.schedule.StartEndTimeException;
import com.novoseltsev.appointmentapi.exception.teacherdetails.TeacherDetailsNotExistException;
import com.novoseltsev.appointmentapi.repository.ScheduleRepository;
import com.novoseltsev.appointmentapi.service.ScheduleService;
import com.novoseltsev.appointmentapi.service.TeacherDetailsService;
import com.novoseltsev.appointmentapi.service.UserService;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ScheduleServiceImpl implements ScheduleService {

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherDetailsService teacherDetailsService;

    @Override
    @Transactional
    public ScheduleDay createDay(ScheduleDay scheduleDay, Long teacherId) {
        checkTimeValidity(Collections.singletonList(scheduleDay));
        teacherDetailsService.addDayToSchedule(scheduleDay, teacherId);
        return scheduleRepository.save(scheduleDay);
    }

    @Override
    public void createAll(List<ScheduleDay> days, Long teacherId) {
        checkTimeValidity(days);
        days.forEach(day -> teacherDetailsService.addDayToSchedule(day,
                teacherId));
        scheduleRepository.saveAll(days);
    }

    @Override
    @Transactional
    public ScheduleDay updateDay(ScheduleDay scheduleDay) {
        ScheduleDay updatingDay = findById(scheduleDay.getId());
        checkTimeValidity(Collections.singletonList(scheduleDay));
        updatingDay.setOpenTimeStart(scheduleDay.getOpenTimeStart());
        updatingDay.setOpenTimeEnd(scheduleDay.getOpenTimeEnd());
        return updatingDay;
    }

    @Override
    @Transactional
    public void updateAll(Queue<ScheduleDay> updatedDays) {
        List<ScheduleDay> savedDays = findAllByIdList(updatedDays.stream()
                .map(ScheduleDay::getId).collect(Collectors.toList()));
        savedDays.forEach(savedDay -> {
            ScheduleDay updatedDay = updatedDays.poll();
            if (updatedDay != null) {
                savedDay.setOpenTimeStart(updatedDay.getOpenTimeStart());
                savedDay.setOpenTimeEnd(updatedDay.getOpenTimeEnd());
            }
        });
    }

    @Override
    @Transactional
    public void deleteDayById(Long id) {
        ScheduleDay day = findById(id);
        scheduleRepository.delete(day);
    }

    @Override
    @Transactional
    public void deleteAll(List<Long> scheduleDayIdList) {
        scheduleRepository.deleteAll(findAllByIdList(scheduleDayIdList));
    }

    @Override
    @Transactional(readOnly = true)
    public ScheduleDay findById(Long id) {
        return scheduleRepository.findById(id)
                .orElseThrow(DayNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDay> findAllByIdList(List<Long> scheduleDayIdList) {
        List<ScheduleDay> days = (List<ScheduleDay>) scheduleRepository
                .findAllById(scheduleDayIdList);
        if (days.size() != scheduleDayIdList.size()) {
            throw new DayNotFoundException();
        }
        return days;
    }

    @Override
    @Transactional(readOnly = true)
    public List<ScheduleDay> findTeacherSchedule(Long teacherId) {
        User user = userService.findById(teacherId);
        TeacherDetails teacherDetails = user.getTeacherDetails();
        if (teacherDetails == null) {
            throw new TeacherDetailsNotExistException("Teacher details are "
                    + "not exist");
        }
        return teacherDetails.getSchedule();
    }

    private void checkTimeValidity(List<ScheduleDay> days) {
        days.forEach(day -> {
            if (day.getOpenTimeEnd().before(day.getOpenTimeStart())) {
                throw new StartEndTimeException("End date less than start date");
            }
        });
    }
}

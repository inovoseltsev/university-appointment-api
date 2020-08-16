package com.novoseltsev.appointmentapi.service.impl;

import com.novoseltsev.appointmentapi.domain.entity.ScheduleDay;
import com.novoseltsev.appointmentapi.domain.entity.TeacherDetails;
import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.exception.teacherdetails.TeacherDetailsNotExistException;
import com.novoseltsev.appointmentapi.exception.time.DayNotFoundException;
import com.novoseltsev.appointmentapi.exception.time.IncorrectTimeIntervalException;
import com.novoseltsev.appointmentapi.exception.time.StartEndTimeException;
import com.novoseltsev.appointmentapi.repository.ScheduleRepository;
import com.novoseltsev.appointmentapi.service.ScheduleService;
import com.novoseltsev.appointmentapi.service.TeacherDetailsService;
import com.novoseltsev.appointmentapi.service.UserService;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
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
        scheduleDay.setId(0L);
        checkTimeValidity(Collections.singletonList(scheduleDay));
        checkDaysTimeIntervalIntersection(Collections
                        .singletonList(scheduleDay),
                findTeacherScheduleDays(teacherId));
        teacherDetailsService.addDayToSchedule(scheduleDay, teacherId);
        return scheduleRepository.save(scheduleDay);
    }

    @Override
    public void createAll(List<ScheduleDay> days, Long teacherId) {
        days.forEach(day -> day.setId(0L));
        checkTimeValidity(days);
        checkDaysTimeIntervalIntersection(days,
                findTeacherScheduleDays(teacherId));
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
        return scheduleRepository.save(updatingDay);
    }

    @Override
    @Transactional
    public void updateAllTeacherDays(
            Queue<ScheduleDay> updatedDays,
            Long teacherId
    ) {
        checkTimeValidity(updatedDays);
        List<ScheduleDay> savedDays = findAllByIdList(updatedDays.stream()
                .map(ScheduleDay::getId).collect(Collectors.toList()));
        List<ScheduleDay> teacherSchedule = findTeacherScheduleDays(teacherId);
        if (!teacherSchedule.containsAll(savedDays)) {
            throw new DayNotFoundException("Teacher doesn't have such days");
        }
        checkDaysTimeIntervalIntersection(updatedDays, teacherSchedule);
        savedDays.forEach(savedDay -> {
            ScheduleDay updatedDay = updatedDays.poll();
            if (updatedDay != null) {
                savedDay.setOpenTimeStart(updatedDay.getOpenTimeStart());
                savedDay.setOpenTimeEnd(updatedDay.getOpenTimeEnd());
            }
        });
        scheduleRepository.saveAll(savedDays);
    }

    @Override
    @Transactional
    public void deleteDayById(Long id) {
        scheduleRepository.delete(findById(id));
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
    public List<ScheduleDay> findTeacherScheduleDays(Long teacherId) {
        User user = userService.findById(teacherId);
        TeacherDetails teacherDetails = user.getTeacherDetails();
        if (teacherDetails == null) {
            throw new TeacherDetailsNotExistException("Teacher details are "
                    + "not exist");
        }
        return teacherDetails.getSchedule();
    }

    private void checkTimeValidity(Collection<ScheduleDay> days) {
        days.forEach(day -> {
            if (day.getOpenTimeEnd().before(day.getOpenTimeStart())
                    || day.getOpenTimeEnd().equals(day.getOpenTimeStart())) {
                throw new StartEndTimeException("End date less than start "
                        + "date");
            }
        });
    }

    private void checkDaysTimeIntervalIntersection(
            Collection<ScheduleDay> days,
            Collection<ScheduleDay> teacherSchedule
    ) {
        days.forEach(day -> teacherSchedule.forEach(teacherDay -> {
            if (!teacherSchedule.isEmpty()) {
                if (!day.getId().equals(teacherDay.getId())) {
                    Date startTime = teacherDay.getOpenTimeStart();
                    Date endTime = teacherDay.getOpenTimeEnd();
                    if (day.getOpenTimeStart().before(endTime) &&
                            day.getOpenTimeEnd().after(startTime)) {
                        throw new IncorrectTimeIntervalException("Open "
                                + "day time intersects with existed");
                    }
                }
            }
        }));
    }
}

package com.novoseltsev.appointmentapi.controller;

import com.novoseltsev.appointmentapi.controller.api.ScheduleApi;
import com.novoseltsev.appointmentapi.domain.dto.ScheduleDayDto;
import com.novoseltsev.appointmentapi.domain.entity.ScheduleDay;
import com.novoseltsev.appointmentapi.service.ScheduleService;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users/teachers/schedule")
@Validated
public class ScheduleController implements ScheduleApi {

    @Autowired
    private ScheduleService scheduleService;

    @Override
    @GetMapping("/{id}")
    public ScheduleDayDto getDayById(@PathVariable Long id) {
        return ScheduleDayDto.fromScheduleDay(scheduleService.findById(id));
    }

    @Override
    @GetMapping("/teacher-days/{teacherId}")
    public List<ScheduleDayDto> getTeacherSchedule(@PathVariable Long teacherId) {
        return scheduleService.findTeacherScheduleDays(teacherId).stream()
            .map(ScheduleDayDto::fromScheduleDay)
            .collect(Collectors.toList());
    }

    @Override
    @PostMapping("/teacher/{teacherId}")
    public ResponseEntity<ScheduleDayDto> createDay(@Valid @RequestBody ScheduleDayDto dayDto, @PathVariable Long teacherId) {
        ScheduleDay createdDay = scheduleService.createDay(dayDto.toScheduleDay(), teacherId);
        return new ResponseEntity<>(ScheduleDayDto.fromScheduleDay(createdDay), HttpStatus.CREATED);
    }

    @Override
    @PostMapping("/teacher-days/{teacherId}")
    public ResponseEntity<HttpStatus> createDays(@PathVariable Long teacherId, @RequestBody List<@Valid ScheduleDayDto> daysDto) {
        List<ScheduleDay> daysToCreate = daysDto.stream()
            .map(ScheduleDayDto::toScheduleDay)
            .collect(Collectors.toList());
        scheduleService.createAll(daysToCreate, teacherId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @PutMapping
    public ScheduleDayDto updateDay(@Valid @RequestBody ScheduleDayDto dayDto) {
        ScheduleDay updatedDay = scheduleService.updateDay(dayDto.toScheduleDay());
        return ScheduleDayDto.fromScheduleDay(updatedDay);
    }

    @Override
    @PutMapping("/teacher-days/{teacherId}")
    public void updateTeacherDays(@RequestBody Queue<@Valid ScheduleDayDto> daysDto, @PathVariable Long teacherId) {
        Queue<ScheduleDay> daysToUpdate = daysDto.stream()
            .map(ScheduleDayDto::toScheduleDay)
            .collect(Collectors.toCollection(LinkedList::new));
        scheduleService.updateAllTeacherDays(daysToUpdate, teacherId);
    }

    @Override
    @DeleteMapping("/{id}")
    public void deleteDay(@PathVariable Long id) {
        scheduleService.deleteDayById(id);
    }

    @Override
    @DeleteMapping
    public void deleteDays(@RequestBody List<Long> dayIdList) {
        scheduleService.deleteAll(dayIdList);
    }
}

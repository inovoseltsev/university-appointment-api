package com.novoseltsev.appointmentapi.controller;

import com.novoseltsev.appointmentapi.domain.dto.AppointmentDto;
import com.novoseltsev.appointmentapi.service.AppointmentService;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/appointments/users")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/appointments/{id}")
    public AppointmentDto getAppointmentById(@PathVariable Long id) {
        return AppointmentDto.from(appointmentService.findById(id));
    }

    @GetMapping("/appointments/many/{userId}")
    public Set<AppointmentDto> getUserAppointments(@PathVariable Long userId) {
        return appointmentService.findUserAppointments(userId).stream()
                .map(AppointmentDto::from).collect(Collectors.toSet());
    }

    @PostMapping("/students/appointments")
    public ResponseEntity<AppointmentDto> create(
            @Valid @RequestBody AppointmentDto appointmentDto
    ) {
        return new ResponseEntity<>(AppointmentDto.from(appointmentService
                .create(appointmentDto.toAppointment())), HttpStatus.CREATED);
    }

    @PutMapping("/students/appointments")
    public AppointmentDto update(
            @Valid @RequestBody AppointmentDto appointmentDto
    ) {
        return AppointmentDto.from(appointmentService
                .update(appointmentDto.toAppointment()));
    }

    @PutMapping("/teachers/appointments/approve/{id}")
    public void approveAppointment(@PathVariable Long id) {
        appointmentService.approveAppointment(id);
    }

    @PutMapping("/appointments/decline/{id}")
    public void declineAppointment(@PathVariable Long id) {
        appointmentService.declineAppointment(id);
    }

    @DeleteMapping("/appointments/{id}")
    public void markAppointmentAsDeleted(@PathVariable Long id) {
        appointmentService.markAppointmentAsDeleted(id);
    }

    @DeleteMapping("/appointments/deletion/{id}")
    public ResponseEntity<HttpStatus> deleteAppointmentById(
            @PathVariable Long id
    ) {
        appointmentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

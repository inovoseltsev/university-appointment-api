package com.novoseltsev.appointmentapi.controller;

import com.novoseltsev.appointmentapi.domain.dto.AppointmentDto;
import com.novoseltsev.appointmentapi.domain.status.AppointmentStatus;
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
@RequestMapping("api/v1/appointments-api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/{id}")
    public AppointmentDto getAppointmentById(@PathVariable Long id) {
        return AppointmentDto.from(appointmentService.findById(id));
    }

    @GetMapping("/user-appointments/{userId}")
    public Set<AppointmentDto> getUserAppointments(@PathVariable Long userId) {
        return appointmentService.findUserAppointments(userId).stream()
                .map(AppointmentDto::from).collect(Collectors.toSet());
    }

    @GetMapping("/confirmation/code/{code}/{appointmentId}")
    public void approveAppointmentByCode(
            @PathVariable String code,
            @PathVariable Long appointmentId) {
        appointmentService.changeAppointmentStatusByCode(appointmentId,
                AppointmentStatus.APPROVED, code);
    }

    @GetMapping("/revocation/code/{code}/{appointmentId}")
    public void declineAppointmentByCode(
            @PathVariable String code,
            @PathVariable Long appointmentId) {
        appointmentService.changeAppointmentStatusByCode(appointmentId,
                AppointmentStatus.DECLINED, code);
    }

    @GetMapping("/cancel-reservation/code/{code}/{appointmentId}")
    public void cancelReservationByCode(
            @PathVariable String code,
            @PathVariable Long appointmentId) {
        appointmentService.changeAppointmentStatusByCode(appointmentId,
                AppointmentStatus.CANCELED, code);
    }

    @PostMapping("/creation")
    public ResponseEntity<AppointmentDto> create(
            @Valid @RequestBody AppointmentDto appointmentDto
    ) {
        return new ResponseEntity<>(AppointmentDto.from(appointmentService
                .create(appointmentDto.toAppointment())), HttpStatus.CREATED);
    }

    @PutMapping("/updating")
    public AppointmentDto update(
            @Valid @RequestBody AppointmentDto appointmentDto
    ) {
        return AppointmentDto.from(appointmentService
                .update(appointmentDto.toAppointment()));
    }

    @PutMapping("/confirmation/{id}")
    public void approveAppointment(@PathVariable Long id) {
        appointmentService.changeAppointmentStatus(id,
                AppointmentStatus.APPROVED);
    }

    @PutMapping("/revocation/{id}")
    public void declineAppointment(@PathVariable Long id) {
        appointmentService.changeAppointmentStatus(id,
                AppointmentStatus.DECLINED);
    }

    @PutMapping("/cancel-reservation/{id}")
    public void cancelReservation(@PathVariable Long id) {
        appointmentService.changeAppointmentStatus(id,
                AppointmentStatus.CANCELED);
    }

    @DeleteMapping("/{id}")
    public void markAppointmentAsDeleted(@PathVariable Long id) {
        appointmentService.markAppointmentAsDeleted(id);
    }

    @DeleteMapping("/deletion/{id}")
    public ResponseEntity<HttpStatus> deleteAppointmentById(
            @PathVariable Long id
    ) {
        appointmentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

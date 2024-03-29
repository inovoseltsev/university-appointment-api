package com.novoseltsev.appointmentapi.controller;

import com.novoseltsev.appointmentapi.controller.api.AppointmentApi;
import com.novoseltsev.appointmentapi.domain.dto.AppointmentDto;
import com.novoseltsev.appointmentapi.domain.entity.Appointment;
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
@RequestMapping("appointments")
public class AppointmentController implements AppointmentApi {

    @Autowired
    private AppointmentService appointmentService;

    @Override
    @GetMapping("/{id}")
    public AppointmentDto getAppointmentById(@PathVariable Long id) {
        return AppointmentDto.from(appointmentService.findById(id));
    }

    @Override
    @GetMapping("/user-appointments/{userId}")
    public Set<AppointmentDto> getUserAppointments(@PathVariable Long userId) {
        return appointmentService.findUserAppointments(userId).stream()
            .map(AppointmentDto::from)
            .collect(Collectors.toSet());
    }

    @Override
    @GetMapping("/confirmation/code/{code}/{appointmentId}")
    public void approveAppointmentByCode(@PathVariable String code, @PathVariable Long appointmentId) {
        appointmentService.changeAppointmentStatusByCode(appointmentId, AppointmentStatus.APPROVED, code);
    }

    @Override
    @GetMapping("/revocation/code/{code}/{appointmentId}")
    public void declineAppointmentByCode(@PathVariable String code, @PathVariable Long appointmentId) {
        appointmentService.changeAppointmentStatusByCode(appointmentId,
                AppointmentStatus.DECLINED, code);
    }

    @Override
    @GetMapping("/cancel-reservation/code/{code}/{appointmentId}")
    public void cancelReservationByCode(@PathVariable String code, @PathVariable Long appointmentId) {
        appointmentService.changeAppointmentStatusByCode(appointmentId, AppointmentStatus.CANCELED, code);
    }

    @Override
    @PostMapping("/creation")
    public ResponseEntity<AppointmentDto> create(@Valid @RequestBody AppointmentDto appointmentDto) {
        Appointment createdAppointment = appointmentService.create(appointmentDto.toAppointment());
        return new ResponseEntity<>(AppointmentDto.from(createdAppointment), HttpStatus.CREATED);
    }

    @Override
    @PutMapping("/updating")
    public AppointmentDto update(@Valid @RequestBody AppointmentDto appointmentDto) {
        Appointment updatedAppointment = appointmentService.update(appointmentDto.toAppointment());
        return AppointmentDto.from(updatedAppointment);
    }

    @Override
    @PutMapping("/confirmation/{id}")
    public void approveAppointment(@PathVariable Long id) {
        appointmentService.changeAppointmentStatus(id, AppointmentStatus.APPROVED);
    }

    @Override
    @PutMapping("/revocation/{id}")
    public void declineAppointment(@PathVariable Long id) {
        appointmentService.changeAppointmentStatus(id, AppointmentStatus.DECLINED);
    }

    @Override
    @PutMapping("/cancel-reservation/{id}")
    public void cancelReservation(@PathVariable Long id) {
        appointmentService.changeAppointmentStatus(id, AppointmentStatus.CANCELED);
    }

    @Override
    @DeleteMapping("/{id}")
    public void markAppointmentAsDeleted(@PathVariable Long id) {
        appointmentService.markAppointmentAsDeleted(id);
    }

    @Override
    @DeleteMapping("/deletion/{id}")
    public ResponseEntity<HttpStatus> deleteAppointmentById(@PathVariable Long id) {
        appointmentService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

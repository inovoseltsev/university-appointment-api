package com.novoseltsev.appointmentapi.controller.api;

import com.novoseltsev.appointmentapi.domain.dto.AppointmentDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "appointment", description = "Teacher-Student appointment API")
public interface AppointmentApi {

    @Operation(summary = "Get appointment by id", tags = "appointment")
    @ApiResponse(
        responseCode = "200",
        description = "Appointment",
        content = {
            @Content(
                schema = @Schema(implementation = AppointmentDto.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    AppointmentDto getAppointmentById(@Parameter(description = "Appointment id", required = true) Long id);

    @Operation(summary = "Get all user appointments", tags = "appointment")
    @ApiResponse(
        responseCode = "200",
        description = "User appointments",
        content = {
            @Content(
                array = @ArraySchema(schema = @Schema(implementation = AppointmentDto.class)),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    Set<AppointmentDto> getUserAppointments(@Parameter(description = "User id", required = true) Long userId);

    @Operation(summary = "Approve appointment by code", tags = "appointment")
    @ApiResponse(responseCode = "200", description = "Appointment is successfully approved")
    void approveAppointmentByCode(@Parameter(description = "Appointment code", required = true) String code,
                                  @Parameter(description = "Appointment id", required = true) Long appointmentId);

    @Operation(summary = "Decline appointment by code", tags = "appointment")
    @ApiResponse(responseCode = "200", description = "Appointment is successfully declined")
    void declineAppointmentByCode(@Parameter(description = "Appointment code", required = true) String code,
                                  @Parameter(description = "Appointment id", required = true) Long appointmentId);

    @Operation(summary = "Cancel reserved appointment time by code", tags = "appointment")
    @ApiResponse(responseCode = "200", description = "Appointment is successfully canceled")
    void cancelReservationByCode(@Parameter(description = "Appointment code", required = true) String code,
                                 @Parameter(description = "Appointment id", required = true) Long appointmentId);

    @Operation(summary = "Create appointment", tags = "appointment")
    @RequestBody(
        description = "Appointment to create",
        required = true,
        content = @Content(
            schema = @Schema(implementation = AppointmentDto.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE
        )
    )
    @ApiResponse(
        responseCode = "201",
        description = "Created appointment",
        content = {
            @Content(
                schema = @Schema(implementation = AppointmentDto.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    ResponseEntity<AppointmentDto> create(AppointmentDto appointmentDto);

    @Operation(summary = "Update appointment", tags = "appointment")
    @RequestBody(
        description = "Appointment to update",
        required = true,
        content = @Content(
            schema = @Schema(implementation = AppointmentDto.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE
        )
    )
    @ApiResponse(
        responseCode = "200",
        description = "Updated appointment",
        content = {
            @Content(
                schema = @Schema(implementation = AppointmentDto.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    AppointmentDto update(AppointmentDto appointmentDto);

    @Operation(summary = "Approve appointment", tags = "appointment")
    @ApiResponse(responseCode = "200", description = "Appointment is successfully approved")
    void approveAppointment(@Parameter(description = "Appointment id", required = true) Long id);

    @Operation(summary = "Decline appointment", tags = "appointment")
    @ApiResponse(responseCode = "200", description = "Appointment is successfully declined")
    void declineAppointment(@Parameter(description = "Appointment id", required = true) Long id);

    @Operation(summary = "Cancel reserved appointment time", tags = "appointment")
    @ApiResponse(responseCode = "200", description = "Appointment is successfully canceled")
    void cancelReservation(@Parameter(description = "Appointment id", required = true) Long id);

    @Operation(summary = "Set appointment status to Deleted", tags = "appointment")
    @ApiResponse(responseCode = "200", description = "Appointment statues is successfully updated")
    void markAppointmentAsDeleted(@Parameter(description = "Appointment id", required = true) Long id);

    @Operation(summary = "Delete appointment", tags = "appointment")
    @ApiResponse(responseCode = "204", description = "Appointment is successfully deleted")
    ResponseEntity<HttpStatus> deleteAppointmentById(@Parameter(description = "Appointment id", required = true) Long id);
}

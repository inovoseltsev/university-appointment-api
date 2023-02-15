package com.novoseltsev.appointmentapi.controller.api;

import com.novoseltsev.appointmentapi.domain.dto.ScheduleDayDto;
import com.novoseltsev.appointmentapi.domain.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.Queue;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "schedule", description = "Teacher's schedule API")
public interface ScheduleApi {

    @Operation(summary = "Get schedule day by id", tags = "schedule")
    @ApiResponse(
        responseCode = "200",
        description = "Schedule day",
        content = {
            @Content(
                schema = @Schema(implementation = ScheduleDayDto.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    ScheduleDayDto getDayById(@Parameter(description = "Schedule day id", required = true) Long id);

    @Operation(summary = "Get user by id", tags = "schedule")
    @ApiResponse(
        responseCode = "200",
        description = "List of Teacher's schedule days",
        content = {
            @Content(
                array = @ArraySchema(schema = @Schema(implementation = UserDto.class)),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    List<ScheduleDayDto> getTeacherSchedule(@Parameter(description = "Teacher id", required = true) Long teacherId);

    @Operation(summary = "Create schedule day for teacher", tags = "schedule")
    @RequestBody(
        description = "Schedule day",
        required = true,
        content = @Content(
            schema = @Schema(implementation = ScheduleDayDto.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE
        )
    )
    @ApiResponse(
        responseCode = "201",
        description = "Day is successfully created",
        content = {
            @Content(
                array = @ArraySchema(schema = @Schema(implementation = UserDto.class)),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    ResponseEntity<ScheduleDayDto> createDay(ScheduleDayDto dayDto,
                                             @Parameter(description = "Teacher id", required = true) Long teacherId);

    @Operation(summary = "Create many schedule days for teacher", tags = "schedule")
    @RequestBody(
        description = "Schedule days with time when teacher is available",
        required = true,
        content = @Content(
            array = @ArraySchema(schema = @Schema(implementation = ScheduleDayDto.class)),
            mediaType = MediaType.APPLICATION_JSON_VALUE
        )
    )
    @ApiResponse(responseCode = "201", description = "Days are successfully created")
    ResponseEntity<HttpStatus> createDays(@Parameter(description = "Teacher id", required = true) Long teacherId,
                                          List<ScheduleDayDto> daysDto);

    @Operation(summary = "Update schedule day time for teacher", tags = "schedule")
    @RequestBody(
        description = "Schedule days with updated time when teacher is available",
        required = true,
        content = @Content(
            array = @ArraySchema(schema = @Schema(implementation = ScheduleDayDto.class)),
            mediaType = MediaType.APPLICATION_JSON_VALUE
        )
    )
    @ApiResponse(
        description = "Day is successfully updated",
        responseCode = "200",
        content = {
            @Content(
                schema = @Schema(implementation = ScheduleDayDto.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        }
    )
    ScheduleDayDto updateDay(@Valid ScheduleDayDto dayDto);

    @Operation(summary = "Update many schedule days for teacher", tags = "schedule")
    @RequestBody(
        description = "Schedule days with updated time when teacher is available",
        required = true,
        content = @Content(
            array = @ArraySchema(schema = @Schema(implementation = ScheduleDayDto.class)),
            mediaType = MediaType.APPLICATION_JSON_VALUE
        )
    )
    @ApiResponse(responseCode = "200", description = "Days are successfully updated")
    void updateTeacherDays(Queue<ScheduleDayDto> daysDto,
                           @Parameter(description = "Teacher id", required = true) Long teacherId);

    @Operation(summary = "Delete schedule day for teacher", tags = "schedule")
    @ApiResponse(responseCode = "200", description = "Day is successfully deleted")
    void deleteDay(@Parameter(description = "Schedule day id", required = true) Long id);

    @Operation(summary = "Delete many schedule days for teacher", tags = "schedule")
    @RequestBody(
        description = "Schedule days ids",
        required = true,
        content = @Content(
            array = @ArraySchema(schema = @Schema(implementation = Long.class)),
            mediaType = MediaType.APPLICATION_JSON_VALUE
        )
    )
    @ApiResponse(responseCode = "200", description = "Days are successfully deleted")
    void deleteDays(List<Long> dayIdList);
}

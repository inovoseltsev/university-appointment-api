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
import org.springframework.web.bind.annotation.PathVariable;

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
    @ApiResponse(
        responseCode = "201",
        description = "Created schedule day",
        content = {
            @Content(
                array = @ArraySchema(schema = @Schema(implementation = UserDto.class)),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    @RequestBody(
        description = "Schedule day",
        required = true,
        content = @Content(
            schema = @Schema(implementation = ScheduleDayDto.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE
        )
    )
    ResponseEntity<ScheduleDayDto> createDay(
        ScheduleDayDto dayDto,
        @Parameter(description = "Teacher id", required = true) Long teacherId
    );

    @Operation(summary = "Create schedule days for teacher", tags = "schedule")
    @ApiResponse(responseCode = "201", description = "Days are successfully created", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    ResponseEntity<HttpStatus> createDays(
        @Parameter(description = "Teacher id", required = true) Long teacherId,
        @RequestBody(
            required = true,
            content = @Content(
                array = @ArraySchema(schema = @Schema(implementation = ScheduleDayDto.class)),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        ) List<ScheduleDayDto> daysDto
    );

    ScheduleDayDto updateDay(@Valid ScheduleDayDto dayDto);

    void updateTeacherDays(Queue<@Valid ScheduleDayDto> daysDto, @PathVariable Long teacherId);

    void deleteDay(@PathVariable Long id);

    void deleteDays(List<Long> dayIdList);
}

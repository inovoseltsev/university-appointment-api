package com.novoseltsev.appointmentapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.appointmentapi.domain.entity.ScheduleDay;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduleDayDto {

    @Positive
    @NotNull
    Long id;

    @NotBlank
    private String openTimeStart;

    @NotBlank
    private String openTimeEnd;

    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("dd-MM-yyyy k:mm");

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public ScheduleDay toScheduleDay() {
        LocalDateTime startDate = LocalDateTime.parse(openTimeStart, FORMATTER);
        LocalDateTime endDate = LocalDateTime.parse(openTimeEnd, FORMATTER);
        ScheduleDay day = new ScheduleDay();
        day.setId(id);
        day.setOpenTimeStart(Timestamp.valueOf(startDate));
        day.setOpenTimeEnd(Timestamp.valueOf(endDate));
        return day;
    }

    public static ScheduleDayDto fromScheduleDay(ScheduleDay day) {
        ScheduleDayDto dayDto = new ScheduleDayDto();
        dayDto.setId(day.getId());
        dayDto.setOpenTimeStart(DATE_FORMAT.format(day.getOpenTimeStart()));
        dayDto.setOpenTimeEnd(DATE_FORMAT.format(day.getOpenTimeEnd()));
        return dayDto;
    }
}

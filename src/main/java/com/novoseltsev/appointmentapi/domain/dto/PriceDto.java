package com.novoseltsev.appointmentapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.novoseltsev.appointmentapi.domain.entity.Price;
import com.novoseltsev.appointmentapi.validation.annotation.TeacherTime;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PriceDto {

    @Positive
    @NotNull
    @Schema(example = "123")
    private Long id;

    @TeacherTime
    @Positive
    @NotNull
    @Schema(example = "60", minimum = "15", maximum = "200")
    private Integer timeInMinutes;

    @Positive
    @NotNull
    @Schema(example = "150", minimum = "0")
    private Integer price;


    public Price toPrice() {
        Price price = new Price();
        price.setId(id);
        price.setTimeInMinutes(timeInMinutes);
        price.setPrice(this.price);
        return price;
    }

    public static PriceDto fromPrice(Price price) {
        PriceDto priceDto = new PriceDto();
        priceDto.setId(price.getId());
        priceDto.setTimeInMinutes(price.getTimeInMinutes());
        priceDto.setPrice(price.getPrice());
        return priceDto;
    }
}

package com.novoseltsev.appointmentapi.controller.api;

import com.novoseltsev.appointmentapi.domain.dto.PriceDto;
import com.novoseltsev.appointmentapi.domain.dto.ScheduleDayDto;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

@Tag(name = "price", description = "Teacher's price API")
public interface PriceApi {

    @Operation(summary = "Get price by id", tags = "price")
    @ApiResponse(
        responseCode = "200",
        description = "Teacher's price",
        content = {
            @Content(
                schema = @Schema(implementation = ScheduleDayDto.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    PriceDto getPriceById(@Parameter(description = "Price id", required = true) Long priceId);

    @Operation(summary = "Get teacher's prices", tags = "price")
    @ApiResponse(
        responseCode = "200",
        description = "Teacher's prices",
        content = {
            @Content(
                array = @ArraySchema(schema = @Schema(implementation = PriceDto.class)),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    List<PriceDto> getTeacherPriceList(@Parameter(description = "Teacher id", required = true) Long teacherId);

    @Operation(summary = "Create price for teacher", tags = "price")
    @RequestBody(
        description = "Price to create",
        required = true,
        content = @Content(
            schema = @Schema(implementation = PriceDto.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE
        )
    )
    @ApiResponse(
        responseCode = "201",
        description = "Teacher's price is successfully created",
        content = {
            @Content(
                schema = @Schema(implementation = PriceDto.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    ResponseEntity<PriceDto> createPriceForTeacher(PriceDto priceDto,
                                                   @Parameter(description = "Teacher id", required = true) Long teacherId);

    @Operation(summary = "Create many prices for teacher", tags = "price")
    @RequestBody(
        description = "Prices to create",
        required = true,
        content = @Content(
            array = @ArraySchema(schema = @Schema(implementation = PriceDto.class)),
            mediaType = MediaType.APPLICATION_JSON_VALUE
        )
    )
    @ApiResponse(responseCode = "201", description = "Teacher prices are successfully created")
    ResponseEntity<HttpStatus> createPricesForTeacher(List<PriceDto> prices,
                                                      @Parameter(description = "Teacher id", required = true) Long teacherId);

    @Operation(summary = "Update price for teacher", tags = "price")
    @RequestBody(
        description = "Price to update",
        required = true,
        content = @Content(
            schema = @Schema(implementation = PriceDto.class),
            mediaType = MediaType.APPLICATION_JSON_VALUE
        )
    )
    @ApiResponse(
        responseCode = "200",
        description = "Teacher's price is successfully updated",
        content = {
            @Content(
                schema = @Schema(implementation = PriceDto.class),
                mediaType = MediaType.APPLICATION_JSON_VALUE
            )
        })
    PriceDto updatePrice(PriceDto priceDto);

    @Operation(summary = "Updated many prices for teacher", tags = "price")
    @RequestBody(
        description = "Prices to update",
        required = true,
        content = @Content(
            array = @ArraySchema(schema = @Schema(implementation = PriceDto.class)),
            mediaType = MediaType.APPLICATION_JSON_VALUE
        )
    )
    @ApiResponse(responseCode = "200", description = "Teacher prices are successfully updated")
    void updatePrices(Queue<PriceDto> pricesDto);

    @Operation(summary = "Delete price for teacher", tags = "price")
    @ApiResponse(responseCode = "200", description = "Price is successfully deleted")
    void deletePriceById(@Parameter(description = "Price id", required = true) Long id);

    @Operation(summary = "Delete many prices for teacher", tags = "price")
    @RequestBody(
        description = "Prices ids",
        required = true,
        content = @Content(
            array = @ArraySchema(schema = @Schema(implementation = Long.class)),
            mediaType = MediaType.APPLICATION_JSON_VALUE
        )
    )
    @ApiResponse(responseCode = "200", description = "Prices are successfully deleted")
    void deletePricesByIdList(List<Long> idList);
}

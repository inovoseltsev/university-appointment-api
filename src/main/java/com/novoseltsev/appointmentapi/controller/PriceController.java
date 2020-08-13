package com.novoseltsev.appointmentapi.controller;

import com.novoseltsev.appointmentapi.domain.dto.PriceDto;
import com.novoseltsev.appointmentapi.domain.entity.Price;
import com.novoseltsev.appointmentapi.service.PriceService;
import java.util.List;
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
@RequestMapping("api/v1/appointments/users/teachers/prices")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @GetMapping("/{priceId}")
    public PriceDto getPriceById(@PathVariable Long priceId) {
        return PriceDto.fromPrice(priceService.findById(priceId));
    }

    @GetMapping("/teacher/{teacherId}")
    public List<PriceDto> getTeacherPriceList(@PathVariable Long teacherId) {
        return priceService.findAllByTeacherId(teacherId).stream()
                .map(PriceDto::fromPrice).collect(Collectors.toList());
    }

    @PostMapping("/teacher/{teacherId}")
    public ResponseEntity<PriceDto> createPriceForTeacher(
            @Valid @RequestBody PriceDto priceDto,
            @PathVariable Long teacherId
    ) {
        Price createdPrice = priceService.create(priceDto.toPrice(), teacherId);
        return new ResponseEntity<>(PriceDto.fromPrice(createdPrice),
                HttpStatus.CREATED);
    }

    @PutMapping
    public PriceDto updatePrice(@Valid @RequestBody PriceDto priceDto) {
        return PriceDto.fromPrice(priceService.update(priceDto.toPrice()));
    }

    @DeleteMapping("/{id}")
    public void deletePriceById(@PathVariable Long id) {
        priceService.deleteById(id);
    }

    @DeleteMapping("/teacher/{teacherId}")
    public void deletePricesByIdList(
            @RequestBody List<Long> idList,
            @PathVariable Long teacherId
    ) {
        priceService.deleteAll(idList, teacherId);
    }
}

package com.novoseltsev.appointmentapi.controller;

import com.novoseltsev.appointmentapi.domain.dto.PriceDto;
import com.novoseltsev.appointmentapi.domain.entity.Price;
import com.novoseltsev.appointmentapi.service.PriceService;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users/teachers/prices")
@Validated
public class PriceController implements com.novoseltsev.appointmentapi.controller.api.PriceApi {

    @Autowired
    private PriceService priceService;

    @Override
    @GetMapping("/{priceId}")
    public PriceDto getPriceById(@PathVariable Long priceId) {
        return PriceDto.fromPrice(priceService.findById(priceId));
    }

    @Override
    @GetMapping("/teacher-prices/{teacherId}")
    public List<PriceDto> getTeacherPriceList(@PathVariable Long teacherId) {
        return priceService.findAllTeacherPrices(teacherId).stream()
            .map(PriceDto::fromPrice)
            .collect(Collectors.toList());
    }

    @Override
    @PostMapping("/teacher/{teacherId}")
    public ResponseEntity<PriceDto> createPriceForTeacher(@Valid @RequestBody PriceDto priceDto, @PathVariable Long teacherId) {
        Price createdPrice = priceService.create(priceDto.toPrice(), teacherId);
        return new ResponseEntity<>(PriceDto.fromPrice(createdPrice), HttpStatus.CREATED);
    }

    @Override
    @PostMapping("/teacher-prices/{teacherId}")
    public ResponseEntity<HttpStatus> createPricesForTeacher(@RequestBody List<@Valid PriceDto> prices, @PathVariable Long teacherId) {
        List<Price> pricesToCreate = prices.stream().map(PriceDto::toPrice).collect(Collectors.toList());
        priceService.createAll(pricesToCreate, teacherId);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    @PutMapping
    public PriceDto updatePrice(@Valid @RequestBody PriceDto priceDto) {
        return PriceDto.fromPrice(priceService.update(priceDto.toPrice()));
    }

    @Override
    @PutMapping("/teacher-prices")
    public void updatePrices(@RequestBody Queue<@Valid PriceDto> pricesDto) {
        Queue<Price> pricesToUpdate = pricesDto.stream()
            .map(PriceDto::toPrice)
            .collect(Collectors.toCollection(LinkedList::new));
        priceService.updateAll(pricesToUpdate);
    }

    @Override
    @DeleteMapping("/{id}")
    public void deletePriceById(@PathVariable Long id) {
        priceService.deleteById(id);
    }

    @Override
    @DeleteMapping
    public void deletePricesByIdList(@RequestBody List<Long> idList) {
        priceService.deleteAll(idList);
    }
}

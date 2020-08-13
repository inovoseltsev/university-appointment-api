package com.novoseltsev.appointmentapi.service;

import com.novoseltsev.appointmentapi.domain.entity.Price;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface PriceService {

    Price create(Price price, Long teacherId);

    Price update(Price price);

    void deleteById(Long id);

    void deleteAll(List<Long> priceIdList, Long teacherId);

    Price findById(Long id);

    List<Price> findAllByIdList(List<Long> priceIdList);

    List<Price> findAllByTeacherId(Long teacherId);
}

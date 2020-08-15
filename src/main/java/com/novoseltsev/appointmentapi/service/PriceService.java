package com.novoseltsev.appointmentapi.service;

import com.novoseltsev.appointmentapi.domain.entity.Price;
import java.util.List;
import java.util.Queue;
import org.springframework.stereotype.Service;

@Service
public interface PriceService {

    Price create(Price price, Long teacherId);

    void createAll(List<Price> prices, Long teacherId);

    Price update(Price price);

    void updateAll(Queue<Price> updatedPriceList);

    void deleteById(Long id);

    void deleteAll(List<Long> priceIdList);

    Price findById(Long id);

    List<Price> findAllByIdList(List<Long> priceIdList);

    List<Price> findAllTeacherPrices(Long teacherId);
}

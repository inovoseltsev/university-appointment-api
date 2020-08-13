package com.novoseltsev.appointmentapi.service;

import com.novoseltsev.appointmentapi.domain.entity.Price;
import org.springframework.stereotype.Service;

@Service
public interface TeacherDetailsService {

    void addPriceToPriceList(Price price, Long teacherId);
}

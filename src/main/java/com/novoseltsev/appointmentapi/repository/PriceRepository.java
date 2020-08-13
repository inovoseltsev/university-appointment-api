package com.novoseltsev.appointmentapi.repository;

import com.novoseltsev.appointmentapi.domain.entity.Price;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceRepository extends CrudRepository<Price, Long> {
}

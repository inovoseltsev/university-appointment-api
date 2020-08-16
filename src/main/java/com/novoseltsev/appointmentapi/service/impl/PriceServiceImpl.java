package com.novoseltsev.appointmentapi.service.impl;

import com.novoseltsev.appointmentapi.domain.entity.Price;
import com.novoseltsev.appointmentapi.domain.entity.TeacherDetails;
import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.domain.entity.abstractentity.AbstractEntity;
import com.novoseltsev.appointmentapi.exception.price.PriceNotFoundException;
import com.novoseltsev.appointmentapi.exception.teacherdetails.TeacherDetailsNotExistException;
import com.novoseltsev.appointmentapi.repository.PriceRepository;
import com.novoseltsev.appointmentapi.service.PriceService;
import com.novoseltsev.appointmentapi.service.TeacherDetailsService;
import com.novoseltsev.appointmentapi.service.UserService;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class PriceServiceImpl implements PriceService {

    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private TeacherDetailsService teacherDetailsService;

    @Override
    @Transactional
    public Price create(Price price, Long teacherId) {
        teacherDetailsService.addPriceToPriceList(price, teacherId);
        return priceRepository.save(price);
    }

    @Override
    @Transactional
    public void createAll(List<Price> prices, Long teacherId) {
        prices.forEach(price -> teacherDetailsService
                .addPriceToPriceList(price, teacherId));
        priceRepository.saveAll(prices);
    }

    @Override
    @Transactional
    public Price update(Price price) {
        Price updatingPrice = findById(price.getId());
        updatingPrice.setTimeInMinutes(price.getTimeInMinutes());
        updatingPrice.setPrice(price.getPrice());
        return priceRepository.save(updatingPrice);
    }

    @Override
    @Transactional
    public void updateAll(Queue<Price> updatedPriceList) {
        List<Price> savedPrices = findAllByIdList(updatedPriceList.stream()
                .map(AbstractEntity::getId).collect(Collectors.toList()));
        savedPrices.forEach(price -> {
            Price queuePrice = updatedPriceList.poll();
            if (queuePrice != null) {
                price.setTimeInMinutes(queuePrice.getTimeInMinutes());
                price.setPrice(queuePrice.getPrice());
            }
        });
        priceRepository.saveAll(savedPrices);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        priceRepository.delete(findById(id));
    }

    @Override
    @Transactional
    public void deleteAll(List<Long> priceIdList) {
        priceRepository.deleteAll(findAllByIdList(priceIdList));
    }

    @Override
    @Transactional(readOnly = true)
    public Price findById(Long id) {
        return priceRepository.findById(id)
                .orElseThrow(PriceNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Price> findAllByIdList(List<Long> priceIdList) {
        List<Price> prices =
                (List<Price>) priceRepository.findAllById(priceIdList);
        if (prices.size() != priceIdList.size()) {
            throw new PriceNotFoundException();
        }
        return prices;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Price> findAllTeacherPrices(Long teacherId) {
        User user = userService.findById(teacherId);
        TeacherDetails teacherDetails = user.getTeacherDetails();
        if (teacherDetails == null) {
            throw new TeacherDetailsNotExistException("Teacher details are "
                    + "not exist");
        }
        return teacherDetails.getPriceList();
    }
}

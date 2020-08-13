package com.novoseltsev.appointmentapi.service.impl;

import com.novoseltsev.appointmentapi.domain.entity.Price;
import com.novoseltsev.appointmentapi.domain.entity.TeacherDetails;
import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.exception.PriceNotFoundException;
import com.novoseltsev.appointmentapi.exception.TeacherDetailsNotExistException;
import com.novoseltsev.appointmentapi.repository.PriceRepository;
import com.novoseltsev.appointmentapi.service.PriceService;
import com.novoseltsev.appointmentapi.service.TeacherDetailsService;
import com.novoseltsev.appointmentapi.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import static com.novoseltsev.appointmentapi.exception.util.ExceptionUtil.checkArgumentForNull;

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
        checkArgumentForNull(price, "create price");
        teacherDetailsService.addPriceToPriceList(price, teacherId);
        return priceRepository.save(price);
    }

    @Override
    @Transactional
    public Price update(Price price) {
        checkArgumentForNull(price, "update price");
        Price updatingPrice = findById(price.getId());
        updatingPrice.setTimeInMinutes(price.getTimeInMinutes());
        updatingPrice.setPrice(price.getPrice());
        return priceRepository.save(updatingPrice);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        checkArgumentForNull(id, "delete price by id");
        Price price = findById(id);
        price.getTeacherDetails().getPriceList().remove(price);
    }

    @Override
    @Transactional
    public void deleteAll(List<Long> priceIdList, Long teacherId) {
        checkArgumentForNull(priceIdList, "delete prices");
        List<Price> prices = findAllByIdList(priceIdList);
        if (!findAllByTeacherId(teacherId).containsAll(prices)) {
            throw new PriceNotFoundException();
        }
        userService.findById(teacherId).getTeacherDetails().getPriceList()
                .removeAll(prices);
    }

    @Override
    @Transactional(readOnly = true)
    public Price findById(Long id) {
        checkArgumentForNull(id, "find price by id");
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
    public List<Price> findAllByTeacherId(Long teacherId) {
        User user = userService.findById(teacherId);
        TeacherDetails teacherDetails = user.getTeacherDetails();
        if (teacherDetails == null) {
            throw new TeacherDetailsNotExistException("Teacher details are "
                    + "not exist");
        }
        return teacherDetails.getPriceList();
    }
}

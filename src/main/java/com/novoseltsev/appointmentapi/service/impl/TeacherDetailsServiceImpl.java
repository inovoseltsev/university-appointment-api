package com.novoseltsev.appointmentapi.service.impl;

import com.novoseltsev.appointmentapi.domain.entity.Price;
import com.novoseltsev.appointmentapi.domain.entity.TeacherDetails;
import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.service.TeacherDetailsService;
import com.novoseltsev.appointmentapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TeacherDetailsServiceImpl implements TeacherDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public void addPriceToPriceList(Price price, Long teacherId) {
        User user = userService.findById(teacherId);
        TeacherDetails teacherDetails = user.getTeacherDetails();
        if (teacherDetails == null) {
            teacherDetails = new TeacherDetails(user);
        }
        price.setId(null);
        price.setTeacherDetails(teacherDetails);
        teacherDetails.getPriceList().add(price);
    }
}

package com.novoseltsev.appointmentapi.service;

import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.domain.entity.UuidUserInfo;
import org.springframework.stereotype.Service;

@Service
public interface UuidUserInfoService {

    UuidUserInfo create(User user);

    UuidUserInfo findByUuid(String uuid);

    void delete(UuidUserInfo uuidUserInfo);
}

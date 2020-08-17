package com.novoseltsev.appointmentapi.service;

import com.novoseltsev.appointmentapi.domain.entity.UuidUserInfo;
import org.springframework.stereotype.Service;

@Service
public interface UuidUserInfoService {

    UuidUserInfo create(Long userId);

    UuidUserInfo findByUuid(String uuid);

    void delete(UuidUserInfo uuidUserInfo);
}

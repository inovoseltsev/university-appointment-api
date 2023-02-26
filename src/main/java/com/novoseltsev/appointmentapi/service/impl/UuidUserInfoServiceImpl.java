package com.novoseltsev.appointmentapi.service.impl;

import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.domain.entity.UuidUserInfo;
import com.novoseltsev.appointmentapi.repository.UuidUserInfoRepository;
import com.novoseltsev.appointmentapi.service.UuidUserInfoService;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UuidUserInfoServiceImpl implements UuidUserInfoService {

    @Autowired
    private UuidUserInfoRepository uuidUserInfoRepository;

    @Override
    @Transactional
    public UuidUserInfo create(User user) {
        UuidUserInfo uuidUserInfo = new UuidUserInfo();
        uuidUserInfo.setUuid(String.valueOf(UUID.randomUUID()));
        uuidUserInfo.setUser(user);
        uuidUserInfoRepository.save(uuidUserInfo);
        return uuidUserInfo;
    }

    @Override
    public UuidUserInfo findByUuid(String uuid) {
        return uuidUserInfoRepository.findByUuid(uuid);
    }

    @Override
    public void delete(UuidUserInfo uuidUserInfo) {
        uuidUserInfoRepository.delete(uuidUserInfo);
    }
}

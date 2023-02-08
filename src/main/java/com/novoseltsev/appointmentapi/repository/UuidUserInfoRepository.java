package com.novoseltsev.appointmentapi.repository;

import com.novoseltsev.appointmentapi.domain.entity.UuidUserInfo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UuidUserInfoRepository extends CrudRepository<UuidUserInfo, Long> {
    UuidUserInfo findByUuid(String uuid);
}

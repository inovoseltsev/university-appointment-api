package com.novoseltsev.appointmentapi.service;

import com.novoseltsev.appointmentapi.domain.entity.User;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User create(User user);

    User update(User user);

    void updatePassword(Long userId, String oldPassword, String newPassword);

    void setDeleted(Long id);

    void deleteById(Long id);

    List<User> findAll();

    User findById(Long id);

    User findByLogin(String login);

    void activateUserByActivationCode(String uuid);

    User findByEmail(String email);
}

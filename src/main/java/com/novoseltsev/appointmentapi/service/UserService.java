package com.novoseltsev.appointmentapi.service;

import com.novoseltsev.appointmentapi.domain.entity.User;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User create(User user);

    User update(User user);

    void delete(User user);

    void deleteById(Long id);

    List<User> findAll();

    User findById(Long id);

    User findByLogin(String login);

    User findByEmail(String email);
}

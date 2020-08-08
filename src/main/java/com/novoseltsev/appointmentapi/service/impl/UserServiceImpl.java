package com.novoseltsev.appointmentapi.service.impl;

import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.domain.status.UserStatus;
import com.novoseltsev.appointmentapi.repository.UserRepository;
import com.novoseltsev.appointmentapi.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;


import static com.novoseltsev.appointmentapi.exception.util.ExceptionUtil.checkArgumentForNull;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public User create(User user) {
        checkArgumentForNull(user, "created");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        checkArgumentForNull(user, "updated");
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        checkArgumentForNull(user, "deleted");
        userRepository.delete(user);
    }

    @Override
    public void deleteById(Long id) {
        checkArgumentForNull(id, "deleted by id");
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        checkArgumentForNull(id, "found by id");
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByLogin(String login) {
        checkArgumentForNull(login, "found by login");
        return userRepository.findByLogin(login);
    }

    @Override
    public User findByEmail(String email) {
        checkArgumentForNull(email, "found by email");
        return userRepository.findByEmail(email);
    }
}

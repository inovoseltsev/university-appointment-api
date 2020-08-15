package com.novoseltsev.appointmentapi.service.impl;

import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.domain.status.UserStatus;
import com.novoseltsev.appointmentapi.exception.user.RegistrationUserException;
import com.novoseltsev.appointmentapi.exception.user.UserNotFoundException;
import com.novoseltsev.appointmentapi.exception.user.UserPasswordUpdateException;
import com.novoseltsev.appointmentapi.repository.UserRepository;
import com.novoseltsev.appointmentapi.service.UserService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public User create(User user) {
        checkLoginAndEmailUniqueness(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.ACTIVE);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public User update(User user) {
        User foundUser = findById(user.getId());
        foundUser.setFirstName(user.getFirstName());
        user.setLastName(user.getLastName());
        user.setEmail(user.getEmail());
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void updatePassword(Long userId, String oldPassword,
                               String newPassword
    ) {
        User user = findById(userId);
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new UserPasswordUpdateException("Old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void markAsDeleted(Long id) {
        User user = findById(id);
        user.setStatus(UserStatus.DELETED);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        findById(id);
        userRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    private void checkLoginAndEmailUniqueness(User checkedUser) {
        String login = checkedUser.getLogin();
        String email = checkedUser.getEmail();
        List<User> users = (List<User>) userRepository.findAll();
        users.forEach(user -> {
            if (user.getLogin().equals(login)) {
                throw new RegistrationUserException("login");
            } else if (user.getEmail().equals(email)) {
                throw new RegistrationUserException("email");
            }
        });
    }
}

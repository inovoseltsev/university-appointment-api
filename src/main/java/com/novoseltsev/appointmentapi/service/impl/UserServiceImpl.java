package com.novoseltsev.appointmentapi.service.impl;

import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.domain.entity.UuidUserInfo;
import com.novoseltsev.appointmentapi.domain.status.UserStatus;
import com.novoseltsev.appointmentapi.exception.user.RegistrationUserException;
import com.novoseltsev.appointmentapi.exception.user.UserNotFoundException;
import com.novoseltsev.appointmentapi.exception.user.UserPasswordUpdateException;
import com.novoseltsev.appointmentapi.repository.UserRepository;
import com.novoseltsev.appointmentapi.service.MailSenderService;
import com.novoseltsev.appointmentapi.service.UserService;
import com.novoseltsev.appointmentapi.service.UuidUserInfoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UuidUserInfoService uuidUserInfoService;

    @Autowired
    private MailSenderService mailSenderService;

    @Value("${server.base.url}")
    private String serverBaseUrl;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    @Transactional
    public User create(User user) {
        //TODO update mapping tables
        checkLoginAndEmailUniqueness(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(UserStatus.NOT_ACTIVE);
        User savedUser = userRepository.save(user);
        sendActivationMessageToUser(user);
        return savedUser;
    }

    @Override
    @Transactional
    public User update(User updatedUser) {
        User foundUser = findById(updatedUser.getId());
        foundUser.setFirstName(updatedUser.getFirstName());
        foundUser.setLastName(updatedUser.getLastName());
        foundUser.setEmail(updatedUser.getEmail());
        return userRepository.save(foundUser);
    }

    @Override
    @Transactional
    public void updatePassword(Long userId, String oldPassword, String newPassword) {
        User user = findById(userId);
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new UserPasswordUpdateException("Old password is incorrect");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void setDeleted(Long id) {
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
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional(readOnly = true)
    public User findByLogin(String login) {
        return userRepository.findByLogin(login).orElseThrow(UserNotFoundException::new);
    }

    @Override
    @Transactional
    public void activateUserByActivationCode(String uuid) {
        UuidUserInfo uuidUserInfo = uuidUserInfoService.findByUuid(uuid);
        if (uuidUserInfo != null) {
            User user = findById(uuidUserInfo.getUserId());
            user.setStatus(UserStatus.ACTIVE);
            userRepository.save(user);
            uuidUserInfoService.delete(uuidUserInfo);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(UserNotFoundException::new);
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

    private void sendActivationMessageToUser(User user) {
        UuidUserInfo uuidUserInfo = uuidUserInfoService.create(user.getId());
        String message = String.format(
            "Hello %s %s!" + System.lineSeparator() + "Please activate your "
                + "account by visiting this link: " + System.lineSeparator()
                + serverBaseUrl + contextPath + "/users/activation/%s",
            user.getFirstName(),
            user.getLastName(),
            uuidUserInfo.getUuid()
        );
        mailSenderService.sendMessage(user.getEmail(), "Account activation", message);
    }
}

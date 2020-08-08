package com.novoseltsev.appointmentapi.service.impl;

import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.domain.role.UserRole;
import com.novoseltsev.appointmentapi.domain.status.UserStatus;
import com.novoseltsev.appointmentapi.repository.UserRepository;
import com.novoseltsev.appointmentapi.service.UserService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private PasswordEncoder passwordEncoder;

    private static User initialUser;

    private static String errorMessage;

    @BeforeAll
    public static void setUp() {
        initialUser = new User("John", "Johnson", "johnjohnson", "johnJohnson"
                + "@gmail.com", "Uu1234");
        initialUser.setId(1L);
        initialUser.setStatus(UserStatus.ACTIVE);
        initialUser.setRole(UserRole.TEACHER);
    }

    @Test
    void create() {
        String initialPassword = initialUser.getPassword();
        String hashPassword =
                new StringBuilder(initialPassword).reverse().toString();
        Mockito.when(passwordEncoder.encode(initialPassword))
                .thenReturn(hashPassword);
        Mockito.when(userRepository.save(initialUser)).thenReturn(initialUser);

        User createdUser = userService.create(initialUser);

        assertNotNull(createdUser);
        assertEquals(hashPassword, createdUser.getPassword());
        assertEquals(UserStatus.ACTIVE, createdUser.getStatus());
        assertEquals(UserRole.TEACHER, createdUser.getRole());
        assertEquals(createdUser, initialUser);
        Mockito.verify(passwordEncoder, Mockito.times(1))
                .encode(initialPassword);
        Mockito.verify(userRepository, Mockito.times(1)).save(initialUser);

        errorMessage = getErrorMessage("created");

        Throwable throwable = assertThrows(IllegalArgumentException.class,
                () -> userService.create(null));
        assertEquals(errorMessage, throwable.getMessage());
    }

    @Test
    void update() {
        Mockito.when(userRepository.save(initialUser)).thenReturn(initialUser);

        User updatedUser = userService.update(initialUser);

        assertNotNull(updatedUser);
        assertEquals(initialUser, updatedUser);
        Mockito.verify(userRepository, Mockito.times(1)).save(initialUser);

        errorMessage = getErrorMessage("updated");

        Throwable throwable = assertThrows(IllegalArgumentException.class,
                () -> userService.update(null));
        assertEquals(errorMessage, throwable.getMessage());
    }

    @Test
    void delete() {
        userService.delete(initialUser);

        Mockito.verify(userRepository, Mockito.times(1)).delete(initialUser);

        errorMessage = getErrorMessage("deleted");

        Throwable throwable = assertThrows(IllegalArgumentException.class,
                () -> userService.delete(null));
        assertEquals(errorMessage, throwable.getMessage());
    }

    @Test
    void deleteById() {
        Long userId = initialUser.getId();
        userService.deleteById(userId);

        Mockito.verify(userRepository, Mockito.times(1)).deleteById(userId);

        errorMessage = getErrorMessage("deleted by id");

        Throwable throwable = assertThrows(IllegalArgumentException.class,
                () -> userService.deleteById(null));
        assertEquals(errorMessage, throwable.getMessage());
    }

    @Test
    void findAll() {
        List<User> expectedUsers = Collections.singletonList(initialUser);
        Mockito.when(userRepository.findAll()).thenReturn(expectedUsers);

        List<User> foundUsers = userService.findAll();

        assertNotNull(foundUsers);
        assertEquals(expectedUsers, foundUsers);
        Mockito.verify(userRepository, Mockito.times(1)).findAll();
    }

    @Test
    void findById() {
        Long existedUserId = initialUser.getId();
        Long notExistedUserId = 0L;
        Mockito.when(userRepository.findById(existedUserId))
                .thenReturn(Optional.of(initialUser));

        User foundUser = userService.findById(existedUserId);

        assertNotNull(foundUser);
        assertEquals(initialUser, foundUser);
        Mockito.verify(userRepository, Mockito.times(1))
                .findById(existedUserId);

        Mockito.when(userRepository.findById(notExistedUserId))
                .thenReturn(Optional.empty());

        User notFoundUser = userService.findById(notExistedUserId);

        assertNull(notFoundUser);

        errorMessage = getErrorMessage("found by id");

        Throwable throwable = assertThrows(IllegalArgumentException.class,
                () -> userService.findById(null));
        assertEquals(errorMessage, throwable.getMessage());
    }

    @Test
    void findByLogin() {
        String existedUserLogin = initialUser.getLogin();
        String notExistedUserLogin = "notExisted";
        Mockito.when(userRepository.findByLogin(existedUserLogin))
                .thenReturn(Optional.of(initialUser));

        User foundUser = userService.findByLogin(existedUserLogin);

        assertNotNull(foundUser);
        assertEquals(initialUser, foundUser);
        Mockito.verify(userRepository, Mockito.times(1))
                .findByLogin(existedUserLogin);

        Mockito.when(userRepository.findByLogin(notExistedUserLogin))
                .thenReturn(Optional.empty());

        User notFoundUser = userService.findByLogin(notExistedUserLogin);

        assertNull(notFoundUser);

        errorMessage = getErrorMessage("found by login");

        Throwable throwable = assertThrows(IllegalArgumentException.class,
                () -> userService.findByLogin(null));
        assertEquals(errorMessage, throwable.getMessage());
    }

    @Test
    void findByEmail() {
        String existedUserEmail = initialUser.getEmail();
        String notExistedUserEmail = "notExisted";
        Mockito.when(userRepository.findUserByEmail(existedUserEmail))
                .thenReturn(Optional.of(initialUser));

        User foundUser = userService.findByEmail(existedUserEmail);

        assertNotNull(foundUser);
        assertEquals(initialUser, foundUser);
        Mockito.verify(userRepository, Mockito.times(1))
                .findUserByEmail(existedUserEmail);

        Mockito.when(userRepository.findUserByEmail(notExistedUserEmail))
                .thenReturn(Optional.empty());

        User notFoundUser = userService.findByEmail(notExistedUserEmail);

        assertNull(notFoundUser);

        errorMessage = getErrorMessage("found by email");

        Throwable throwable = assertThrows(IllegalArgumentException.class,
                () -> userService.findByEmail(null));
        assertEquals(errorMessage, throwable.getMessage());
    }

    private String getErrorMessage(String processName) {
        return "User cannot be " + processName + " because argument "
                + "is null!";
    }
}
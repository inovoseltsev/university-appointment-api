package com.novoseltsev.appointmentapi.repository;

import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.domain.role.UserRole;
import com.novoseltsev.appointmentapi.domain.status.UserStatus;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestEntityManager entityManager;

    private static User initialUser;

    @BeforeAll
    public static void setUp() {
        initialUser = new User("John", "Johnson", "johnjohnson", "johnJohnson"
                + "@gmail.com", "Uu1234");
        initialUser.setStatus(UserStatus.ACTIVE);
        initialUser.setRole(UserRole.TEACHER);
    }

    @Test
    void findByLogin() {
        entityManager.persistAndFlush(initialUser);

    }

    @Test
    void findByEmail() {
        entityManager.merge(initialUser);
    }
}
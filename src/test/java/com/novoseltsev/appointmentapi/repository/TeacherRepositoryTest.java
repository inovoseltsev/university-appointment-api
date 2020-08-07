package com.novoseltsev.appointmentapi.repository;

import com.novoseltsev.appointmentapi.domain.entity.Teacher;
import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.domain.role.UserRole;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@DataJpaTest
class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private TestEntityManager entityManager;

    private static Teacher initialTeacher;

    private static Teacher foundTeacher;

    @BeforeAll
    public static void setUp() {
        User user = new User("John", "Johnson", "johnJohnson",
                "johnJohnson"
                + "@gmail.com", "1234", UserRole.TEACHER);
        initialTeacher = new Teacher(user);
    }

    @Test
    void findTeacherByUser() {
        Teacher expectedTeacher = entityManager.persistAndFlush(initialTeacher);
        foundTeacher =
                teacherRepository.findTeacherByUser(expectedTeacher.getUser());
        assertNotNull(foundTeacher);
        assertEquals(expectedTeacher, foundTeacher);

        foundTeacher = teacherRepository.findTeacherByUser(null);
        assertNull(foundTeacher);
    }

    @Test
    void findTeacherByUserId() {
        Teacher expectedTeacher = entityManager.merge(initialTeacher);
        foundTeacher =
                teacherRepository.findTeacherByUserId(expectedTeacher.getUser()
                        .getId());
        assertNotNull(foundTeacher);
        assertEquals(expectedTeacher, foundTeacher);

        foundTeacher = teacherRepository.findTeacherByUserId(null);
        assertNull(foundTeacher);
    }
}
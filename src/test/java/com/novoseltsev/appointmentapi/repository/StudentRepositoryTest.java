package com.novoseltsev.appointmentapi.repository;

import com.novoseltsev.appointmentapi.domain.entity.Student;
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
class StudentRepositoryTest {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TestEntityManager entityManager;

    private static Student initialStudent;

    private static Student foundStudent;

    @BeforeAll
    public static void setUp() {
        User initialUser = new User("John", "Johnson", "johnJohnson", "johnJohnson"
                + "@gmail.com", "1234", UserRole.STUDENT);
        initialStudent = new Student(initialUser);
    }

    @Test
    void findStudentByUser() {
        Student expectedStudent = entityManager.persistAndFlush(initialStudent);
        foundStudent =
                studentRepository.findStudentByUser(expectedStudent.getUser());
        assertEquals(expectedStudent, foundStudent);
        assertNotNull(foundStudent);

        Student wrongStudent = studentRepository.findStudentByUser(null);
        assertNull(wrongStudent);
    }

    @Test
    void findStudentByUserId() {
        Student expectedStudent = entityManager.merge(initialStudent);
        foundStudent =
                studentRepository.findStudentByUserId(expectedStudent.getUser()
                        .getId());
        assertEquals(expectedStudent, foundStudent);
        assertNotNull(foundStudent);

        foundStudent = studentRepository.findStudentByUserId(null);
        assertNull(foundStudent);
    }
}
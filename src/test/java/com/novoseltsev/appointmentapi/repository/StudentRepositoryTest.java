//package com.novoseltsev.appointmentapi.repository;
//
//import com.novoseltsev.appointmentapi.domain.entity.Student;
//import com.novoseltsev.appointmentapi.domain.entity.User;
//import com.novoseltsev.appointmentapi.domain.role.UserRole;
//import com.novoseltsev.appointmentapi.domain.status.UserStatus;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
//import org.springframework.test.context.junit4.SpringRunner;
//
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
//@RunWith(SpringRunner.class)
//@DataJpaTest
//class StudentRepositoryTest {
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    @Autowired
//    private TestEntityManager entityManager;
//
//    private static Student initialStudent;
//
//    @BeforeAll
//    public static void setUp() {
//        User user = new User("John", "Johnson", "johnjohnson", "johnJohnson"
//                + "@gmail.com", "Uu1234");
//        user.setStatus(UserStatus.ACTIVE);
//        user.setRole(UserRole.STUDENT);
//        initialStudent = new Student(user);
//    }
//
//    @Test
//    void findStudentByUserId() {
//        Student expectedStudent = entityManager.persistAndFlush(initialStudent);
//        Long existedId = expectedStudent.getId();
//        Long notExistedId = 0L;
//        Student foundStudent =
//                studentRepository.findStudentByUserId(existedId);
//
//        assertNotNull(foundStudent);
//        assertEquals(expectedStudent, foundStudent);
//
//        foundStudent = studentRepository.findStudentByUserId(notExistedId);
//
//        assertNull(foundStudent);
//    }
//}
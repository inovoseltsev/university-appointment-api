//package com.novoseltsev.appointmentapi.service.impl;
//
//import com.novoseltsev.appointmentapi.domain.entity.Student;
//import com.novoseltsev.appointmentapi.domain.entity.User;
//import com.novoseltsev.appointmentapi.domain.role.UserRole;
//import com.novoseltsev.appointmentapi.domain.status.UserStatus;
//import com.novoseltsev.appointmentapi.repository.StudentRepository;
//import com.novoseltsev.appointmentapi.service.StudentService;
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.mockito.Mockito;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.junit4.SpringRunner;
//
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNotNull;
//import static org.junit.jupiter.api.Assertions.assertNull;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//class StudentServiceImplTest {
//
//    @Autowired
//    private StudentService studentService;
//
//    @MockBean
//    private StudentRepository studentRepository;
//
//    @MockBean
//    private PasswordEncoder passwordEncoder;
//
//    private static Student initialStudent;
//
//    private static User initialUser;
//
//    private static String exceptionMessage;
//
//    @BeforeAll
//    public static void setUp() {
//        initialUser = new User("John", "Johnson", "johnjohnson", "johnJohnson"
//                + "@gmail.com", "Uu1234");
//        initialUser.setId(1L);
//        initialStudent = new Student(initialUser);
//        initialStudent.setId(1L);
//    }
//
//    @Test
//    void create() {
//        String initialPassword = initialUser.getPassword();
//        String hashPassword =
//                new StringBuilder(initialPassword).reverse().toString();
//        Mockito.when(studentRepository.save(initialStudent))
//                .thenReturn(initialStudent);
//        Mockito.when(passwordEncoder.encode(initialPassword))
//                .thenReturn(hashPassword);
//
//        Student createdStudent = studentService.create(initialStudent);
//
//        assertNotNull(createdStudent);
//        assertEquals(UserStatus.ACTIVE, createdStudent.getUser().getStatus());
//        assertEquals(UserRole.STUDENT, createdStudent.getUser().getRole());
//        assertEquals(hashPassword, createdStudent.getUser().getPassword());
//        assertEquals(initialStudent, createdStudent);
//        Mockito.verify(studentRepository, Mockito.times(1))
//                .save(initialStudent);
//        Mockito.verify(passwordEncoder, Mockito.times(1))
//                .encode(initialPassword);
//
//        exceptionMessage = getExceptionMessage("created");
//
//        Throwable exception = assertThrows(IllegalArgumentException.class,
//                () -> studentService.create(null));
//
//        assertEquals(exceptionMessage, exception.getMessage());
//    }
//
//    @Test
//    void update() {
//        Mockito.when(studentRepository.save(initialStudent))
//                .thenReturn(initialStudent);
//
//        Student updatedUser = studentService.update(initialStudent);
//
//        assertNotNull(updatedUser);
//        assertEquals(initialStudent, updatedUser);
//        Mockito.verify(studentRepository, Mockito.times(1))
//                .save(initialStudent);
//
//        exceptionMessage = getExceptionMessage("updated");
//
//        Throwable exception = assertThrows(IllegalArgumentException.class,
//                () -> studentService.update(null));
//
//        assertEquals(exceptionMessage, exception.getMessage());
//    }
//
//    @Test
//    void delete() {
//        studentService.delete(initialStudent);
//
//        Mockito.verify(studentRepository, Mockito.times(1))
//                .delete(initialStudent);
//
//        exceptionMessage = getExceptionMessage("deleted");
//
//        Throwable exception = assertThrows(IllegalArgumentException.class,
//                () -> studentService.delete(null));
//
//        assertEquals(exceptionMessage, exception.getMessage());
//    }
//
//    @Test
//    void deleteById() {
//        studentService.deleteById(initialStudent.getId());
//
//        Mockito.verify(studentRepository, Mockito.times(1))
//                .deleteById(initialStudent.getId());
//
//        exceptionMessage = getExceptionMessage("deleted by id");
//
//        Throwable exception = assertThrows(IllegalArgumentException.class,
//                () -> studentService.deleteById(null));
//
//        assertEquals(exceptionMessage, exception.getMessage());
//    }
//
//    @Test
//    void findAll() {
//        Mockito.when(studentRepository.findAll()).thenReturn(Collections
//                .singletonList(initialStudent));
//
//        List<Student> foundStudents = studentService.findAll();
//
//        assertNotNull(foundStudents);
//        assertEquals(Collections.singletonList(initialStudent), foundStudents);
//        Mockito.verify(studentRepository, Mockito.times(1)).findAll();
//    }
//
//    @Test
//    void findById() {
//        Long existedId = initialStudent.getId();
//        Long notExistedId = 0L;
//        Mockito.when(studentRepository.findById(initialStudent.getId()))
//                .thenReturn(Optional.of(initialStudent));
//
//        Student foundStudent =
//                studentService.findById(existedId);
//
//        assertNotNull(foundStudent);
//        assertEquals(initialStudent, foundStudent);
//        Mockito.verify(studentRepository, Mockito.times(1))
//                .findById(initialStudent.getId());
//
//        Mockito.when(studentRepository.findById(notExistedId))
//                .thenReturn(Optional.empty());
//
//        Student notFoundStudent = studentService.findById(notExistedId);
//
//        assertNull(notFoundStudent);
//
//        exceptionMessage = getExceptionMessage("found by id");
//
//        Throwable exception = assertThrows(IllegalArgumentException.class,
//                () -> studentService.findById(null));
//
//        assertEquals(exceptionMessage, exception.getMessage());
//    }
//
//    @Test
//    void findByUserId() {
//        Long existedUserId = initialStudent.getId();
//        Long notExistedUserId = 0L;
//        Mockito.when(studentRepository.findStudentByUserId(existedUserId))
//                .thenReturn(initialStudent);
//
//        Student foundStudent = studentService.findByUserId(existedUserId);
//
//        assertNotNull(foundStudent);
//        assertEquals(initialStudent, foundStudent);
//        Mockito.verify(studentRepository, Mockito.times(1))
//                .findStudentByUserId(existedUserId);
//
//        Student notFoundStudent = studentService.findByUserId(notExistedUserId);
//
//        assertNull(notFoundStudent);
//
//        exceptionMessage = getExceptionMessage("found by userId");
//
//        Throwable exception = assertThrows(IllegalArgumentException.class,
//                () -> studentService.findByUserId(null));
//
//        assertEquals(exceptionMessage, exception.getMessage());
//    }
//
//    private String getExceptionMessage(String processName) {
//        return "Student cannot be " + processName + " because argument "
//                + "is null!";
//    }
//}
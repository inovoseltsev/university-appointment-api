//package com.novoseltsev.appointmentapi.service.impl;
//
//import com.novoseltsev.appointmentapi.domain.entity.Student;
//import com.novoseltsev.appointmentapi.domain.entity.User;
//import com.novoseltsev.appointmentapi.domain.role.UserRole;
//import com.novoseltsev.appointmentapi.domain.status.UserStatus;
//import com.novoseltsev.appointmentapi.repository.StudentRepository;
//import com.novoseltsev.appointmentapi.service.StudentService;
//import java.util.Date;
//import java.util.List;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//
//import static com.novoseltsev.appointmentapi.validation.message.ErrorMessageUtil.STUDENT_OBJECT;
//import static com.novoseltsev.appointmentapi.validation.message.ErrorMessageUtil.checkArgumentForNull;
//
//@Component
//public class StudentServiceImpl implements StudentService {
//
//    @Autowired
//    private StudentRepository studentRepository;
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Override
//    @Transactional
//    public Student create(Student student) {
//        checkArgumentForNull(student, STUDENT_OBJECT, "created");
//        User user = student.getUser();
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        user.setStatus(UserStatus.ACTIVE);
//        user.setRole(UserRole.STUDENT);
//        return studentRepository.save(student);
//    }
//
//    @Override
//    @Transactional
//    public Student update(Student student) {
//        checkArgumentForNull(student, STUDENT_OBJECT, "updated");
//        student.setUpdateDate(new Date());
//        return studentRepository.save(student);
//    }
//
//    @Override
//    @Transactional
//    public void delete(Student student) {
//        checkArgumentForNull(student, STUDENT_OBJECT, "deleted");
//        studentRepository.delete(student);
//    }
//
//    @Override
//    @Transactional
//    public void deleteById(Long id) {
//        checkArgumentForNull(id, STUDENT_OBJECT, "deleted by id");
//        studentRepository.deleteById(id);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<Student> findAll() {
//        return (List<Student>) studentRepository.findAll();
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Student findById(Long id) {
//        checkArgumentForNull(id, STUDENT_OBJECT, "found by id");
//        return studentRepository.findById(id).orElse(null);
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Student findByUserId(Long userId) {
//        checkArgumentForNull(userId, STUDENT_OBJECT, "found by userId");
//        return studentRepository.findStudentByUserId(userId);
//    }
//}

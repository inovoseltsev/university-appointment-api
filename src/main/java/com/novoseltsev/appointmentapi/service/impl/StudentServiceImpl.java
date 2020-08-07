package com.novoseltsev.appointmentapi.service.impl;

import com.novoseltsev.appointmentapi.domain.entity.Student;
import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.repository.StudentRepository;
import com.novoseltsev.appointmentapi.service.StudentService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Override
    @Transactional
    public Student create(Student student) {
        String errorMessage = "Student is null during creation process!";
        checkForNullValue(student, errorMessage);
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public Student update(Student student) {
        student.setUpdateDate(new Date());
        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public void delete(Student student) {
        studentRepository.delete(student);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Student> findAll() {
        return (List<Student>) studentRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Student findById(Long id) {
        return studentRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Student findByUser(User user) {
        return studentRepository.findStudentByUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Student findByUserId(Long userId) {
        return studentRepository.findStudentByUserId(userId);
    }

    private void checkForNullValue(Object obj, String errorMessage) {
        if (obj == null) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}

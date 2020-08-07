package com.novoseltsev.appointmentapi.service;

import com.novoseltsev.appointmentapi.domain.entity.Student;
import com.novoseltsev.appointmentapi.domain.entity.User;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface StudentService {

    Student create(Student student);

    Student update(Student student);

    void delete(Student student);

    void deleteById(Long id);

    List<Student> findAll();

    Student findById(Long id);

    Student findByUser(User user);

    Student findByUserId(Long userId);
}

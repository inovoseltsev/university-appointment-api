package com.novoseltsev.appointmentapi.service;

import com.novoseltsev.appointmentapi.domain.entity.Teacher;
import com.novoseltsev.appointmentapi.domain.entity.User;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public interface TeacherService {

    Teacher create(Teacher teacher);

    Teacher update(Teacher teacher);

    void delete(Teacher teacher);

    void deleteById(Long id);

    List<Teacher> findAll();

    Teacher findById(Long id);

    Teacher findByUser(User user);

    Teacher findByUserId(Long userId);
}

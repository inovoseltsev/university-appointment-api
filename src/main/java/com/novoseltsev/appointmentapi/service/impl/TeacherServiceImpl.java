package com.novoseltsev.appointmentapi.service.impl;

import com.novoseltsev.appointmentapi.domain.entity.Teacher;
import com.novoseltsev.appointmentapi.domain.entity.User;
import com.novoseltsev.appointmentapi.repository.TeacherRepository;
import com.novoseltsev.appointmentapi.service.TeacherService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Override
    @Transactional
    public Teacher create(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    @Transactional
    public Teacher update(Teacher teacher) {
        teacher.setUpdateDate(new Date());
        return teacherRepository.save(teacher);
    }

    @Override
    @Transactional
    public void delete(Teacher teacher) {
        teacherRepository.delete(teacher);
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        teacherRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Teacher> findAll() {
        return (List<Teacher>) teacherRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Teacher findById(Long id) {
        return teacherRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Teacher findByUser(User user) {
        return teacherRepository.findTeacherByUser(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Teacher findByUserId(Long userId) {
        return teacherRepository.findTeacherByUserId(userId);
    }
}

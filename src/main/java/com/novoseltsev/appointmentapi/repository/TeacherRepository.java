package com.novoseltsev.appointmentapi.repository;

import com.novoseltsev.appointmentapi.domain.entity.Teacher;
import com.novoseltsev.appointmentapi.domain.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends CrudRepository<Teacher, Long> {
    Teacher findTeacherByUser(User user);
}

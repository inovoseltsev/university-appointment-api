package com.novoseltsev.appointmentapi.domain.entity;

import com.novoseltsev.appointmentapi.domain.entity.abstractentity.AbstractEntity;
import com.novoseltsev.appointmentapi.domain.role.UserRole;
import com.novoseltsev.appointmentapi.domain.status.AppointmentStatus;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import static com.novoseltsev.appointmentapi.exception.util.ExceptionUtil.checkForRoleMatching;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Appointment extends AbstractEntity {

    @Column(name = "start_date")
    @FutureOrPresent
    @NotNull
    private Date startDate;

    @Column(name = "end_date")
    @Future
    @NotNull
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "usr_teacher_id", nullable = false)
    @NotNull
    private User teacher;

    @ManyToOne
    @JoinColumn(name = "usr_student_id", nullable = false)
    @NotNull
    private User student;

    @Column(length = 25)
    @Enumerated(value = EnumType.STRING)
    private AppointmentStatus status = AppointmentStatus.PENDING;

    public Appointment(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setTeacher(User teacher) {
        checkForRoleMatching(teacher, UserRole.STUDENT);
        this.teacher = teacher;
    }

    public void setStudent(User student) {
        checkForRoleMatching(student, UserRole.TEACHER);
        this.student = student;
    }
}

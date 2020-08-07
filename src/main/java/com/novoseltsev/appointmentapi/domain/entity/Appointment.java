package com.novoseltsev.appointmentapi.domain.entity;

import com.novoseltsev.appointmentapi.domain.entity.abstractentity.AbstractEntity;
import com.novoseltsev.appointmentapi.domain.status.AppointmentStatus;
import java.sql.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

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
    @JoinColumn(nullable = false)
    @NotNull
    private Teacher teacher;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private Student student;

    @Column(length = 25)
    @Enumerated(value = EnumType.STRING)
    private AppointmentStatus status = AppointmentStatus.PENDING;

    public Appointment() {
    }

    public Appointment(Date startDate, Date endDate, Teacher teacher,
                       Student student) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.teacher = teacher;
        this.student = student;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public AppointmentStatus getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Appointment that = (Appointment) o;
        return startDate.equals(that.startDate) &&
                endDate.equals(that.endDate) &&
                teacher.equals(that.teacher) &&
                student.equals(that.student) &&
                status == that.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(startDate, endDate, teacher, student, status);
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", teacher=" + teacher +
                ", student=" + student +
                ", status=" + status +
                '}';
    }
}

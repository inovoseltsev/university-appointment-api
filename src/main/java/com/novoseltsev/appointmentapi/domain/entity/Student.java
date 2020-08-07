package com.novoseltsev.appointmentapi.domain.entity;

import com.novoseltsev.appointmentapi.domain.entity.abstractentity.AbstractEntity;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Student extends AbstractEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, unique = true)
    @NotNull
    private User user;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    public void addAppointment(Appointment appointment) {
        appointment.setStudent(this);
        appointments.add(appointment);
    }

    public Student() {
    }

    public Student(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Appointment> appointments) {
        this.appointments = appointments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return user.equals(student.user) &&
                Objects.equals(appointments, student.appointments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, appointments);
    }

    @Override
    public String toString() {
        return "Student{" +
                "user=" + user +
                ", appointments=" + appointments +
                '}';
    }
}

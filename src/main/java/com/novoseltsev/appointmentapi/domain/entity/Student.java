package com.novoseltsev.appointmentapi.domain.entity;

import com.novoseltsev.appointmentapi.domain.entity.abstractentity.AbstractEntity;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Student extends AbstractEntity {

    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "student")
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
}

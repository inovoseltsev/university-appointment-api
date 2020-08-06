package com.novoseltsev.appointmentapi.domain.entity;

import com.novoseltsev.appointmentapi.domain.entity.abstractentity.AbstractEntity;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Teacher extends AbstractEntity {

    @OneToOne
    @JoinColumn(nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "teacher")
    private List<Appointment> appointments;

    public void addAppointment(Appointment appointment) {
        appointment.setTeacher(this);
        this.appointments.add(appointment);
    }

    @OneToMany(mappedBy = "teacher")
    private List<ScheduleDay> schedule;

    @ManyToMany
    @JoinTable(name = "teacher_price")
    private List<Price> priceList;

    public Teacher() {
    }

    public Teacher(User user) {
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

    public List<ScheduleDay> getSchedule() {
        return schedule;
    }

    public void setSchedule(List<ScheduleDay> schedule) {
        this.schedule = schedule;
    }
}

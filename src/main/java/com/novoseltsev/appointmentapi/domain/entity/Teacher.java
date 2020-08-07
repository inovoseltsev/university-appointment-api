package com.novoseltsev.appointmentapi.domain.entity;

import com.novoseltsev.appointmentapi.domain.entity.abstractentity.AbstractEntity;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Teacher extends AbstractEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false, unique = true)
    @NotNull
    private User user;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<@NotNull Appointment> appointments;

    public void addAppointment(Appointment appointment) {
        appointment.setTeacher(this);
        this.appointments.add(appointment);
    }

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL)
    private List<@NotNull ScheduleDay> schedule;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "teacher_price")
    private List<@NotNull Price> priceList;

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

    public List<Price> getPriceList() {
        return priceList;
    }

    public void setPriceList(List<Price> priceList) {
        this.priceList = priceList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Teacher teacher = (Teacher) o;
        return user.equals(teacher.user) &&
                Objects.equals(appointments, teacher.appointments) &&
                Objects.equals(schedule, teacher.schedule) &&
                Objects.equals(priceList, teacher.priceList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, appointments, schedule, priceList);
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "user=" + user +
                ", appointments=" + appointments +
                ", schedule=" + schedule +
                ", priceList=" + priceList +
                '}';
    }
}

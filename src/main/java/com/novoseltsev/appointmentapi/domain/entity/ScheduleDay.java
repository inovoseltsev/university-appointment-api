package com.novoseltsev.appointmentapi.domain.entity;

import com.novoseltsev.appointmentapi.domain.entity.abstractentity.AbstractEntity;
import java.sql.Date;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;

@Entity
public class ScheduleDay extends AbstractEntity {

    @Column(nullable = false)
    @FutureOrPresent
    @NotNull
    private Date openTimeStart;

    @Column(nullable = false)
    @Future
    @NotNull
    private Date openTimeEnd;

    @ManyToOne
    @JoinColumn(nullable = false)
    @NotNull
    private Teacher teacher;

    public ScheduleDay() {
    }

    public ScheduleDay(Date openTimeStart, Date openTimeEnd, Teacher teacher) {
        this.openTimeStart = openTimeStart;
        this.openTimeEnd = openTimeEnd;
        this.teacher = teacher;
    }

    public Date getOpenTimeStart() {
        return openTimeStart;
    }

    public void setOpenTimeStart(Date openTimeStart) {
        this.openTimeStart = openTimeStart;
    }

    public Date getOpenTimeEnd() {
        return openTimeEnd;
    }

    public void setOpenTimeEnd(Date openTimeEnd) {
        this.openTimeEnd = openTimeEnd;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScheduleDay that = (ScheduleDay) o;
        return openTimeStart.equals(that.openTimeStart) &&
                openTimeEnd.equals(that.openTimeEnd) &&
                teacher.equals(that.teacher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(openTimeStart, openTimeEnd, teacher);
    }

    @Override
    public String toString() {
        return "ScheduleDay{" +
                "openTimeStart=" + openTimeStart +
                ", openTimeEnd=" + openTimeEnd +
                ", teacher=" + teacher +
                '}';
    }
}

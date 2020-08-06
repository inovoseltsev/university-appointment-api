package com.novoseltsev.appointmentapi.domain.entity;

import com.novoseltsev.appointmentapi.domain.entity.abstractentity.AbstractEntity;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class ScheduleDay extends AbstractEntity {

    @Column(nullable = false)
    private Date openTimeStart;

    @Column(nullable = false)
    private Date openTimeEnd;

    @ManyToOne
    @JoinColumn(nullable = false)
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
}

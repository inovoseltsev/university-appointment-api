package com.novoseltsev.appointmentapi.domain.entity;

import com.novoseltsev.appointmentapi.domain.entity.abstractentity.AbstractEntity;
import java.sql.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(nullable = false)
    private TeacherDetails teacherDetails;

    public ScheduleDay(Date openTimeStart, Date openTimeEnd, TeacherDetails teacherDetails) {
        this.openTimeStart = openTimeStart;
        this.openTimeEnd = openTimeEnd;
        this.teacherDetails = teacherDetails;
    }
}

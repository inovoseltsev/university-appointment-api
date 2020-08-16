package com.novoseltsev.appointmentapi.domain.entity;

import com.novoseltsev.appointmentapi.domain.entity.abstractentity.AbstractEntity;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class ScheduleDay extends AbstractEntity {

    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    @FutureOrPresent
    @NotNull
    private Date openTimeStart;

    @Column(nullable = false)
    @Temporal(value = TemporalType.TIMESTAMP)
    @Future
    @NotNull
    private Date openTimeEnd;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_details_id", nullable = false)
    @ToString.Exclude
    private TeacherDetails teacherDetails;
}

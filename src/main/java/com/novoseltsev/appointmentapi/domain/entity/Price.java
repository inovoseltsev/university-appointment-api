package com.novoseltsev.appointmentapi.domain.entity;

import com.novoseltsev.appointmentapi.domain.entity.abstractentity.AbstractEntity;
import com.novoseltsev.appointmentapi.validation.TeacherTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Price extends AbstractEntity {

    @Column(nullable = false)
    @TeacherTime
    @Positive
    @NotNull
    private Integer time;

    @Column(nullable = false)
    @Positive
    @NotNull
    private Integer price;

    @ManyToOne
    @JoinColumn(nullable = false)
    private TeacherDetails teacherDetails;

    public Price(Integer time, Integer price, TeacherDetails teacherDetails) {
        this.time = time;
        this.price = price;
        this.teacherDetails = teacherDetails;
    }
}

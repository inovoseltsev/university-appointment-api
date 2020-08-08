package com.novoseltsev.appointmentapi.domain.entity;

import com.novoseltsev.appointmentapi.domain.entity.abstractentity.AbstractEntity;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class TeacherDetails extends AbstractEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, unique = true)
    private User user;

    @OneToMany(mappedBy = "teacherDetails", cascade = CascadeType.ALL)
    private List<Price> priceList;

    @OneToMany(mappedBy = "teacherDetails", cascade = CascadeType.ALL)
    private List<ScheduleDay> schedule;

    public TeacherDetails(User user, List<Price> priceList) {
        this.user = user;
        this.priceList = priceList;
    }

    public TeacherDetails(List<ScheduleDay> schedule, User user) {
        this.user = user;
        this.schedule = schedule;
    }
}

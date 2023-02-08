package com.novoseltsev.appointmentapi.domain.entity;

import com.novoseltsev.appointmentapi.domain.entity.abstractentity.AbstractEntity;
import java.util.ArrayList;
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

    @OneToMany(mappedBy = "teacherDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Price> priceList = new ArrayList<>();

    @OneToMany(mappedBy = "teacherDetails", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ScheduleDay> schedule = new ArrayList<>();

    public TeacherDetails(User user) {
        this.user = user;
        user.setTeacherDetails(this);
    }
}

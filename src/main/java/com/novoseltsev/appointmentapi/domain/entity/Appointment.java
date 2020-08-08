package com.novoseltsev.appointmentapi.domain.entity;

import com.novoseltsev.appointmentapi.domain.entity.abstractentity.AbstractEntity;
import com.novoseltsev.appointmentapi.domain.status.AppointmentStatus;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;


import static com.novoseltsev.appointmentapi.exception.util.ExceptionUtil.checkAppointmentUsersForRoleMatching;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Appointment extends AbstractEntity {

    @Column(name = "start_date")
    @FutureOrPresent
    @NotNull
    private Date startDate;

    @Column(name = "end_date")
    @Future
    @NotNull
    private Date endDate;

    @ManyToMany(mappedBy = "appointments", cascade = CascadeType.ALL)
    @NotNull
    @Size(min = 2, max = 2)
    @Setter(value = AccessLevel.NONE)
    private List<User> users;

    @Column(length = 25, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AppointmentStatus status = AppointmentStatus.PENDING;

    public Appointment(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public void setUsers(User teacher, User student) {
        checkAppointmentUsersForRoleMatching(teacher, student);
        this.users = Arrays.asList(teacher, student);
    }
}

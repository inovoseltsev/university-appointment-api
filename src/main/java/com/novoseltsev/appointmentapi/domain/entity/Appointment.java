package com.novoseltsev.appointmentapi.domain.entity;

import com.novoseltsev.appointmentapi.domain.entity.abstractentity.AbstractEntity;
import com.novoseltsev.appointmentapi.domain.status.AppointmentStatus;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


import static com.novoseltsev.appointmentapi.exception.util.ExceptionUtil.checkAppointmentUsersForRoleMatching;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
public class Appointment extends AbstractEntity {

    @Column(name = "start_date")
    @FutureOrPresent
    @NotNull
    private Date startTime;

    @Column(name = "end_date")
    @Future
    @NotNull
    private Date endTime;

    @NotNull
    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "usr_appointment", joinColumns = @JoinColumn(name =
            "appointment_id", nullable = false), inverseJoinColumns =
    @JoinColumn(name = "user_id", nullable = false))
    private Set<User> users = new HashSet<>();

    @Column(length = 25, nullable = false)
    @Enumerated(value = EnumType.STRING)
    private AppointmentStatus status = AppointmentStatus.PENDING;

    public void setUsers(User student, User teacher) {
        checkAppointmentUsersForRoleMatching(student, teacher);
        this.users.addAll(Arrays.asList(student, teacher));
    }
}

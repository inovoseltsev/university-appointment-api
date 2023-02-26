package com.novoseltsev.appointmentapi.domain.entity;

import com.novoseltsev.appointmentapi.domain.entity.abstractentity.AbstractEntity;
import com.novoseltsev.appointmentapi.domain.role.UserRole;
import com.novoseltsev.appointmentapi.domain.status.UserStatus;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import static com.novoseltsev.appointmentapi.validation.message.ValidationMessageUtil.EMAIL_ERROR;
import static com.novoseltsev.appointmentapi.validation.message.ValidationMessageUtil.FIRST_NAME_ERROR;
import static com.novoseltsev.appointmentapi.validation.message.ValidationMessageUtil.LAST_NAME_ERROR;
import static com.novoseltsev.appointmentapi.validation.message.ValidationMessageUtil.LOGIN_ERROR;
import static com.novoseltsev.appointmentapi.validation.message.ValidationMessageUtil.PASSWORD_ERROR;
import static com.novoseltsev.appointmentapi.validation.regexp.PatternUtil.EMAIL_PATTERN;
import static com.novoseltsev.appointmentapi.validation.regexp.PatternUtil.LOGIN_PATTERN;
import static com.novoseltsev.appointmentapi.validation.regexp.PatternUtil.NAME_PATTERN;

@Data
@NoArgsConstructor
@EqualsAndHashCode(of = {"email", "login"}, callSuper = true)
@Entity
@Table(name = "usr")
public class User extends AbstractEntity {

    @Column(nullable = false)
    @NotBlank(message = FIRST_NAME_ERROR)
    @Pattern(regexp = NAME_PATTERN, message = FIRST_NAME_ERROR)
    private String firstName;

    @Column(nullable = false)
    @NotBlank(message = LAST_NAME_ERROR)
    @Pattern(regexp = NAME_PATTERN, message = LAST_NAME_ERROR)
    private String lastName;

    @Column(nullable = false, unique = true)
    @NotBlank(message = LOGIN_ERROR)
    @Pattern(regexp = LOGIN_PATTERN, message = LOGIN_ERROR)
    private String login;

    @Column(nullable = false, unique = true)
    @NotBlank(message = EMAIL_ERROR)
    @Pattern(regexp = EMAIL_PATTERN, message = EMAIL_ERROR)
    private String email;

    @Column(nullable = false)
    @NotBlank(message = PASSWORD_ERROR)
    private String password;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 25, nullable = false)
    private UserStatus status;

    @Enumerated(value = EnumType.STRING)
    @Column(length = 25, nullable = false)
    private UserRole role;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    @ToString.Exclude
    private TeacherDetails teacherDetails;

    @ManyToMany(mappedBy = "users", fetch = FetchType.LAZY)
    private Set<Appointment> appointments = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UuidUserInfo> uuidUsers = new ArrayList<>();

    public void addAppointment(Appointment appointment) {
        this.appointments.add(appointment);
        appointment.getUsers().add(this);
    }

    public void removeAppointment(Appointment appointment) {
        this.appointments.remove(appointment);
        appointment.getUsers().remove(this);
    }

    public void addUUIDUser(UuidUserInfo uuidUser) {
        uuidUser.setUser(this);
        this.uuidUsers.add(uuidUser);
    }

    public User(String firstName, String lastName, String login, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.email = email;
        this.password = password;
    }
}

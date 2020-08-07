package com.novoseltsev.appointmentapi.domain.entity;

import com.novoseltsev.appointmentapi.domain.entity.abstractentity.AbstractEntity;
import com.novoseltsev.appointmentapi.validation.TeacherTime;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


@Entity
public class Price extends AbstractEntity {

    @Column(nullable = false)
    @TeacherTime
    @NotNull
    private Integer time;

    @Column(nullable = false)
    @Positive
    @NotNull
    private Integer price;

    @ManyToMany(mappedBy = "priceList")
    @NotNull
    @NotEmpty
    private List<@NotNull Teacher> teachers;

    public Price() {
    }

    public Price(Integer time, Integer price, List<Teacher> teachers) {
        this.time = time;
        this.price = price;
        this.teachers = teachers;
    }

    public Integer getTime() {
        return time;
    }

    public void setTime(Integer time) {
        this.time = time;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Price price1 = (Price) o;
        return time.equals(price1.time) &&
                price.equals(price1.price) &&
                teachers.equals(price1.teachers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(time, price, teachers);
    }

    @Override
    public String toString() {
        return "Price{" +
                "time=" + time +
                ", price=" + price +
                ", teachers=" + teachers +
                '}';
    }
}

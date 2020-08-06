package com.novoseltsev.appointmentapi.domain.entity;

import com.novoseltsev.appointmentapi.domain.entity.abstractentity.AbstractEntity;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;


@Entity
public class Price extends AbstractEntity {

    @Column(nullable = false)
    private Integer time;

    @Column(nullable = false)
    private Integer price;

    @ManyToMany(mappedBy = "priceList")
    private List<Teacher> teachers;

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
}

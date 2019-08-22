package com.microservice.weighttrackerapp.tracker;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import java.util.Date;
import java.util.Objects;

@Entity
public class Measure implements Comparable<Measure> {
    @Id
    @GeneratedValue
    private long id;

    @Column(name = "person_name")
    private String name;

    @DecimalMin(value = "0.1", message = "! Weight should be above 0 kg")
    private float weight;

    private String notes;

    @Column(name = "measure_date")
    private Date date;

    public Measure() {
    }

    public Measure(String name, float weight, String notes, Date date) {
        this.name = name;
        this.weight = weight;
        this.notes = notes;
        this.date = date;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Measure measure = (Measure) o;
        return id == measure.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Measure{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight +
                ", notes='" + notes + '\'' +
                ", date=" + date +
                '}';
    }

    @Override
    public int compareTo(Measure o) {
        // return date.compareTo(o.getDate());
        return o.getDate().compareTo(this.date);
    }
}

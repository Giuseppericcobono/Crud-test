package co.develhope.crud_test.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "students")
public class Students {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column
    private Boolean isWorking;

    public Students(){}

    public Students(Integer id, String name, String surname, Boolean isWorking) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.isWorking = isWorking;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public Boolean getWorking() {
        return isWorking;
    }

    public void setWorking(Boolean working) {
        isWorking = working;
    }
}

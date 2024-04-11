package com.luis.curso.springboot.jpa.springbootjpa.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "persons")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Column(name = "lastname")
    private String lastName;

    @Column(name = "programming_language")
    private String programmingLanguaje;

    public Person() {
    }

    public Person(Long id, String name, String lastName, String programmingLanguaje) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.programmingLanguaje = programmingLanguaje;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getProgrammingLanguaje() {
        return programmingLanguaje;
    }

    public void setProgrammingLanguaje(String programmingLanguaje) {
        this.programmingLanguaje = programmingLanguaje;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", programmingLanguaje='" + programmingLanguaje + '\'' +
                '}';
    }
}

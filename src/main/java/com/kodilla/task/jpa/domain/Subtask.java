package com.kodilla.task.jpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Subtask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String status;

    @OneToMany(targetEntity = Person.class, mappedBy = "subtask")
    private Set<Person> persons = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    public Subtask() {
    }

    public Subtask(Long id, String name, String status, Task task) {
        this.id = id;
        this.name = name;
        this.status = status;
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public Set<Person> getPersons() {
        return persons;
    }

    public Task getTask() {
        return task;
    }
}

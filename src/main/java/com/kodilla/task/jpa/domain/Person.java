package com.kodilla.task.jpa.domain;

import com.kodilla.jpa.domain.Invoice;

import javax.persistence.*;

@Entity
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstname;
    private String lastname;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "subtask_id")
    private Subtask subtask;

    public Person() {
    }

    public Person(Long id, String firstname, String lastname, Task task, Subtask subtask) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.task = task;
        this.subtask = subtask;
    }

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Task getTask() {
        return task;
    }

    public Subtask getSubtask() {
        return subtask;
    }
}
